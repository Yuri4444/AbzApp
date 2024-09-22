package com.yuri_berezhnyi.abzapp.ui.signUp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yuri_berezhnyi.abzapp.R
import com.yuri_berezhnyi.abzapp.data.cloud.UserRequest
import com.yuri_berezhnyi.abzapp.databinding.BottomSheetDialogBinding
import com.yuri_berezhnyi.abzapp.databinding.FragmentSignUpBinding
import com.yuri_berezhnyi.abzapp.ui.core.ui.viewBinding
import com.yuri_berezhnyi.abzapp.ui.utils.Validation
import com.yuri_berezhnyi.abzapp.ui.utils.gone
import com.yuri_berezhnyi.abzapp.ui.utils.invisible
import com.yuri_berezhnyi.abzapp.ui.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel by viewModels<SignUpViewModel>()
    private var selectedPositionIndex: Int = 1

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var takePhotoLauncher: ActivityResultLauncher<Intent>

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private var photoFile: File? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            val adapterPositions = PositionAdapter {
                selectedPositionIndex = it
            }

            rvPositions.adapter = adapterPositions

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.positions.collectLatest(adapterPositions::submitList)
                    }
                }
            }

            btnSignUp.setOnClickListener {
                //Name
                if (etYourName.text.isNullOrBlank()) {
                    etYourName.isWork(false)
                    tvYourNameError.visible()
                } else {
                    etYourName.isWork(true)
                    tvYourNameError.invisible()
                }
                //Email
                if (!Validation.validateEmail(binding.etEmail.text.toString())) {
                    etEmail.isWork(false)
                    tvEmailError.visible()
                } else {
                    etEmail.isWork(true)
                    tvEmailError.invisible()
                }
                //Phone
                if (etPhoneNumber.text.isNullOrBlank()) {
                    etPhoneNumber.isWork(false)
                    tvPhoneClue.gone()
                    tvYourNameError.visible()
                } else {
                    etPhoneNumber.isWork(true)
                    tvPhoneClue.visible()
                    tvYourNameError.gone()
                }
                //Photo
                if (photoFile != null) {
                    llUploadYourPhoto.setBackgroundResource(R.drawable.bg_edit_error)
                    tvImageName.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                    tvPhotoError.visible()
                } else {
                    llUploadYourPhoto.setBackgroundResource(R.drawable.bg_edit)
                    tvImageName.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                    tvPhotoError.invisible()
                }

                if (!etYourName.text.isNullOrBlank() &&
                    Validation.validateEmail(binding.etEmail.text.toString()) &&
                    !etPhoneNumber.text.isNullOrBlank() &&
                    photoFile != null
                ) {
                    viewModel.registrationUser(
                        UserRequest(
                            name = etYourName.text.toString(),
                            email = etEmail.text.toString(),
                            phone = etPhoneNumber.text.toString(),
                            positionId = selectedPositionIndex,
                            photo = photoFile!!
                        )
                    )
                }
            }

            llUploadYourPhoto.setOnClickListener {
                showBottomSheetDialog()
            }
        }

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
                val readStorageGranted =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissions[Manifest.permission.READ_MEDIA_IMAGES] ?: false
                    } else {
                        permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false
                    }

                when {
                    cameraGranted -> openCamera()
                    readStorageGranted -> openGallery()
                    else -> Toast.makeText(
                        requireContext(),
                        getString(R.string.permissions_not_granted),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.data?.let { uri ->
                        val bitmap = uriToBitmap(uri)
                        val fileName = getFileNameFromUri(uri)
                        photoFile = bitmap?.let { saveBitmapToFile(it, fileName ?: "selected.jpeg") }
                        binding.tvImageName.text = fileName ?: getString(R.string.image_selected)
                    }
                }
            }

        takePhotoLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val imageBitmap = result.data?.extras?.get("data") as? Bitmap
                    imageBitmap?.let {
                        photoFile = saveBitmapToFile(it, "captured_photo.jpg")
                        binding.tvImageName.text = getString(R.string.your_photo)
                    }
                }
            }

        viewModel.fetchPositions()
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = BottomSheetDialogBinding.inflate(layoutInflater)

        bottomSheetBinding.llCamera.setOnClickListener {
            requestCameraPermission()
            bottomSheetDialog.dismiss()
        }

        bottomSheetBinding.llGallery.setOnClickListener {
            requestGalleryPermission()
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun requestCameraPermission() {
        permissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
    }

    private fun requestGalleryPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(arrayOf(Manifest.permission.READ_MEDIA_IMAGES))
        } else {
            permissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoLauncher.launch(intent)
    }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun saveBitmapToFile(bitmap: Bitmap, fileName: String): File? {
        return try {
            val file = File(context?.cacheDir, fileName)
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getFileNameFromUri(uri: Uri): String? {
        var fileName: String? = null
        val cursor = context?.contentResolver?.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                fileName = it.getString(nameIndex)
            }
        }
        return fileName
    }
}