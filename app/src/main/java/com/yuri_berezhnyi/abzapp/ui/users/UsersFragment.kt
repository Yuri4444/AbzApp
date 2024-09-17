package com.yuri_berezhnyi.abzapp.ui.users

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yuri_berezhnyi.abzapp.R
import com.yuri_berezhnyi.abzapp.databinding.FragmentUsersBinding
import com.yuri_berezhnyi.abzapp.ui.core.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users) {

    private val binding by viewBinding(FragmentUsersBinding::bind)
    private val viewModel by viewModels<UsersViewModel>()
    private val adapter by lazy { UsersAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvUsers.adapter = adapter

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.pagedRequest.collectLatest(adapter::submitData)
                    }
                }
            }
        }
    }
}