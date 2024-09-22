package com.yuri_berezhnyi.abzapp.ui.users

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.yuri_berezhnyi.abzapp.R
import com.yuri_berezhnyi.abzapp.databinding.FragmentUsersBinding
import com.yuri_berezhnyi.abzapp.ui.core.ui.viewBinding
import com.yuri_berezhnyi.abzapp.ui.utils.customeView.UsersLoadStateAdapter
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

            rvUsers.adapter = adapter.withLoadStateFooter(
                footer = UsersLoadStateAdapter { adapter.retry() }
            )

            adapter.addLoadStateListener { loadState ->
                binding.progressBarFooter.isVisible = loadState.source.refresh is LoadState.Loading
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.users.collectLatest(adapter::submitData)
                }
            }
        }
    }
}