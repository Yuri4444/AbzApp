package com.yuri_berezhnyi.abzapp.ui.signUp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yuri_berezhnyi.abzapp.R
import com.yuri_berezhnyi.abzapp.databinding.ItemPositionBinding
import com.yuri_berezhnyi.abzapp.ui.users.PositionUI

class PositionAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<PositionUI, PositionAdapter.PositionViewHolder>(PositionDiffUtil()) {

    private var selectedItemPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_position, parent, false)
        return PositionViewHolder(ItemPositionBinding.bind(view))
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class PositionViewHolder(
        private val binding: ItemPositionBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PositionUI, position: Int) {
            with(binding) {

                root.setOnClickListener {
                    if (selectedItemPosition != position) {
                        val previousSelectedItemPosition = selectedItemPosition
                        selectedItemPosition = position

                        notifyItemChanged(previousSelectedItemPosition)
                        notifyItemChanged(position)

                        onClick(item.id)
                    }
                }

                rbItem.isChecked = selectedItemPosition == position
                rbItem.text = item.name
            }
        }
    }

    class PositionDiffUtil : DiffUtil.ItemCallback<PositionUI>() {
        override fun areItemsTheSame(oldItem: PositionUI, newItem: PositionUI) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PositionUI, newItem: PositionUI) =
            oldItem == newItem
    }
}