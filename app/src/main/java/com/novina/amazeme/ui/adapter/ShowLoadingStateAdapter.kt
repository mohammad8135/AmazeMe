package com.novina.amazeme.ui.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.novina.amazeme.ui.viewholder.PageLoadStateViewHolder

class ShowLoadingStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PageLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = PageLoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(
        holder: PageLoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}