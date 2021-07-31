package com.novina.amazeme.ui.showlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.novina.amazeme.R
import com.novina.amazeme.databinding.ShowListFragmentBinding
import com.novina.amazeme.ui.main.ShowListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowListFragment : Fragment() {

    private lateinit var viewModel: ShowListViewModel
    private lateinit var binding: ShowListFragmentBinding
    private val adapter = ShowListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ShowListFragmentBinding.inflate(inflater, container, false).apply {
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    DividerItemDecoration.HORIZONTAL
                )
            )
            subscribeToData()
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShowListViewModel::class.java)
    }

    private fun subscribeToData() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                adapter.submitData(
                    state.pagingData
                )
            }
        }
    }
}