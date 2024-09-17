package com.yuri_berezhnyi.abzapp.ui.noInternet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.yuri_berezhnyi.abzapp.R
import com.yuri_berezhnyi.abzapp.databinding.FragmentNoInternetBinding
import com.yuri_berezhnyi.abzapp.ui.core.ui.viewBinding

class NoInternetFragment : Fragment(R.layout.fragment_no_internet) {

    private val binding by viewBinding(FragmentNoInternetBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}