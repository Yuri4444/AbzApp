package com.yuri_berezhnyi.abzapp.ui.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.yuri_berezhnyi.abzapp.R
import com.yuri_berezhnyi.abzapp.databinding.FragmentFailtureBinding
import com.yuri_berezhnyi.abzapp.ui.core.ui.viewBinding

class FailureFragment : Fragment(R.layout.fragment_failture) {

    private val binding by viewBinding(FragmentFailtureBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}