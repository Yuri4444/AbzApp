package com.yuri_berezhnyi.abzapp.ui.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.yuri_berezhnyi.abzapp.R
import com.yuri_berezhnyi.abzapp.databinding.FragmentSuccessBinding
import com.yuri_berezhnyi.abzapp.ui.core.ui.viewBinding

class SuccessFragment : Fragment(R.layout.fragment_success) {

    private val binding by viewBinding(FragmentSuccessBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}