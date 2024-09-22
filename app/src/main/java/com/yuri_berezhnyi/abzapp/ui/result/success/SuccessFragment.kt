package com.yuri_berezhnyi.abzapp.ui.result.success

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yuri_berezhnyi.abzapp.R
import com.yuri_berezhnyi.abzapp.databinding.FragmentSuccessBinding
import com.yuri_berezhnyi.abzapp.ui.core.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessFragment : Fragment(R.layout.fragment_success) {

    private val binding by viewBinding(FragmentSuccessBinding::bind)
    private val viewModel by viewModels<SuccessViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            ivClose.setOnClickListener {
                viewModel.goBack()
            }

            btnGotIt.setOnClickListener {
                viewModel.goBack()
            }
        }
    }

}