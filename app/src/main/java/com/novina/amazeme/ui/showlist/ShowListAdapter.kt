package com.novina.amazeme.ui.showlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.novina.amazeme.R
import com.novina.amazeme.data.model.Show
import com.novina.amazeme.ui.showlist.ShowItemViewHolder

class ShowListAdapter() :
    PagingDataAdapter<Show, ShowItemViewHolder>(ShowDiffCallback()) {

    override fun onBindViewHolder(holder: ShowItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item == null) {
            holder.bindPlaceHolder()
        } else {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowItemViewHolder {
        return ShowItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.show_list_item,
                parent,
                false
            )
        )
    }

    class ShowDiffCallback : DiffUtil.ItemCallback<Show>() {

        override fun areItemsTheSame(
            oldItem: Show,
            newItem: Show
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Show,
            newItem: Show
        ): Boolean {
            return oldItem == newItem
        }
    }
}