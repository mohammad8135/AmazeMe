package com.novina.amazeme.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.novina.amazeme.R
import com.novina.amazeme.databinding.ItemGridLoadStateBinding

class PageLoadStateViewHolder(
    parent: ViewGroup,
    retry: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_grid_load_state, parent, false)
) {
    private val binding = ItemGridLoadStateBinding.bind(itemView)
    private val progressBar: ProgressBar = binding.progressBar
    private val retry: ImageButton = binding.retryButton
        .also {
            it.setOnClickListener {
                retry()
            }
        }

    fun bind(loadState: LoadState) {
        progressBar.isVisible = loadState is LoadState.Loading
        retry.isVisible = loadState is LoadState.Error
    }
}