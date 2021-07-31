package com.novina.amazeme.ui.showlist

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.novina.amazeme.data.model.Show
import com.novina.amazeme.databinding.ShowListItemBinding

class ShowItemViewHolder(private val binding: ShowListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.setClickListener { view ->
            binding.viewModel?.showId?.let { showId ->
                if (showId > 0)
                    navigateToShowDetail(showId = showId, view)
            }
        }
    }

    fun bind(show: Show) {
        with(binding) {
            viewModel = ShowListItemViewModel(show)
            executePendingBindings()
        }
    }

    fun bindPlaceHolder() {
        with(binding) {
            viewModel = ShowListItemViewModel(Show(-1, "Loading ..."))
            executePendingBindings()
        }
    }

    private fun navigateToShowDetail(showId: Int, view: View) {
        val direction =
            ShowListFragmentDirections.actionShowListFragmentToShowDetailFragment(showId)
        view.findNavController().navigate(direction)
    }

}