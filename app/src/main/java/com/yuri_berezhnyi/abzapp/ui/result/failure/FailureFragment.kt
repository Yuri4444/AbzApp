package com.yuri_berezhnyi.abzapp.ui.result.failure

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yuri_berezhnyi.abzapp.R
import com.yuri_berezhnyi.abzapp.databinding.FragmentFailtureBinding
import com.yuri_berezhnyi.abzapp.ui.core.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FailureFragment : Fragment(R.layout.fragment_failture) {

    private val binding by viewBinding(FragmentFailtureBinding::bind)
    private val viewModel by viewModels<FailureViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            ivClose.setOnClickListener {
                viewModel.goBack()
            }

            btnTryAgain.setOnClickListener {
                viewModel.goBack()
            }
        }
    }

}