package com.yuri_berezhnyi.abzapp.ui.signUp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.yuri_berezhnyi.abzapp.R
import com.yuri_berezhnyi.abzapp.databinding.FragmentSignUpBinding
import com.yuri_berezhnyi.abzapp.ui.core.ui.viewBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}