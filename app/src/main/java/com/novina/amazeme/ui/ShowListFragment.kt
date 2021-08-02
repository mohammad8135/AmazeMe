package com.novina.amazeme.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.novina.amazeme.databinding.FragmentShowListBinding
import com.novina.amazeme.ui.adapter.ShowListAdapter
import com.novina.amazeme.ui.adapter.ShowLoadingStateAdapter
import com.novina.amazeme.ui.showlist.ShowListViewModel
import com.novina.amazeme.util.localizedString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowListFragment : Fragment() {

    private lateinit var viewModel: ShowListViewModel
    private lateinit var binding: FragmentShowListBinding
    private val pagingAdapter = ShowListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowListBinding.inflate(inflater, container, false).apply {
            with(recyclerView) {
                adapter = pagingAdapter.withLoadStateFooter(
                    footer = ShowLoadingStateAdapter(pagingAdapter::retry)
                )
                addItemDecoration(
                    DividerItemDecoration(
                        context,
                        DividerItemDecoration.VERTICAL
                    )
                )
                addItemDecoration(
                    DividerItemDecoration(
                        context,
                        DividerItemDecoration.HORIZONTAL
                    )
                )
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewState()
        subscribeToLoadingState()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShowListViewModel::class.java)
    }

    private fun subscribeToViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                pagingAdapter.submitData(state.pagingData)
            }
        }
    }

    private fun subscribeToLoadingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collectLatest { state ->
                onLoadingStateChanged(state)
            }
        }
    }

    private fun onLoadingStateChanged(state: CombinedLoadStates) {
        with(binding) {
            isLoading = false
            errorMessage = ""
            hasShows = pagingAdapter.itemCount != 0
            if (pagingAdapter.itemCount == 0) {
                when (state.refresh) {
                    is LoadState.Loading -> {
                        isLoading = true
                        errorMessage = ""
                    }
                    is LoadState.Error -> {
                        isLoading = false
                        loadStateErrorContainer.retryButton.setOnClickListener { pagingAdapter.refresh() }
                        errorMessage = (state.refresh as LoadState.Error).error.localizedString(resources)

                    }
                    else -> isLoading = false
                }
            }
        }
    }
}