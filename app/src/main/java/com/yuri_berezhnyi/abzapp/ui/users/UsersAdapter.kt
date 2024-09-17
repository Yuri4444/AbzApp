package com.yuri_berezhnyi.abzapp.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yuri_berezhnyi.abzapp.R
import com.yuri_berezhnyi.abzapp.databinding.ItemUserBinding

class UsersAdapter : PagingDataAdapter<UserUi, UsersAdapter.UsersViewHolder>(UsersDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UsersViewHolder(ItemUserBinding.bind(view))
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class UsersViewHolder(
        private val binding: ItemUserBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserUi) =
            with(binding) {
                tvName.text = item.name
                Glide.with(root).load(item.photo).circleCrop().into(ivAvatar)
                tvPosition.text = item.position
                tvEmail.text = item.email
                tvPhoneNumber.text = item.phone
            }
    }

    class UsersDiffUtil : DiffUtil.ItemCallback<UserUi>() {
        override fun areItemsTheSame(oldItem: UserUi, newItem: UserUi) =
            oldItem.phone == newItem.phone

        override fun areContentsTheSame(oldItem: UserUi, newItem: UserUi) =
            oldItem == newItem
    }
}