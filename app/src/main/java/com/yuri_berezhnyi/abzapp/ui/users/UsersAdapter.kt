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

    // Создание ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UsersViewHolder(ItemUserBinding.bind(view))
    }

    // Привязка данных к ViewHolder
    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        // Получение элемента на позиции и привязка данных
        getItem(position)?.let { holder.bind(it) }
    }

    // ViewHolder для пользователя
    inner class UsersViewHolder(
        private val binding: ItemUserBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        // Привязка данных пользователя к ViewHolder
        fun bind(item: UserUi) = with(binding) {
            tvName.text = item.name
            Glide.with(root)
                .load(item.photo) // Загрузка фото пользователя с помощью Glide
                .circleCrop()     // Применение круговой маски к изображению
                .into(ivAvatar)   // Установка изображения в ImageView
            tvPosition.text = item.position
            tvEmail.text = item.email
            tvPhoneNumber.text = item.phone
        }
    }

    // DiffUtil для сравнения элементов
    class UsersDiffUtil : DiffUtil.ItemCallback<UserUi>() {
        // Сравнение элементов по уникальному идентификатору
        override fun areItemsTheSame(oldItem: UserUi, newItem: UserUi) =
            oldItem.phone == newItem.phone // Используем телефон как уникальный идентификатор

        // Сравнение содержимого элементов
        override fun areContentsTheSame(oldItem: UserUi, newItem: UserUi) =
            oldItem == newItem // Сравниваем полностью объекты
    }
}
