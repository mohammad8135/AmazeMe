package com.novina.amazeme.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.novina.amazeme.R
import com.novina.amazeme.data.model.Show
import com.novina.amazeme.ui.viewholder.ShowListItemViewHolder

class ShowListAdapter() :
    PagingDataAdapter<Show, ShowListItemViewHolder>(ShowDiffCallback()) {

    override fun onBindViewHolder(holder: ShowListItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item == null) {
            holder.bindPlaceHolder()
        } else {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowListItemViewHolder {
        return ShowListItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_grid_show,
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