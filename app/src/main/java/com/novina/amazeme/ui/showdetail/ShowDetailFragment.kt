package com.novina.amazeme.ui.showdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.novina.amazeme.R
import com.novina.amazeme.databinding.ShowDetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShowDetailFragment : Fragment() {

    @Inject
    lateinit var showDetailViewModelFactory: ShowDetailViewModelFactory
    private val args: ShowDetailFragmentArgs by navArgs()
    private val showDetailViewModel: ShowDetailViewModel  by viewModels {
        ShowDetailViewModel.provideFactory(showDetailViewModelFactory, args.showId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ShowDetailFragmentBinding>(
            inflater,
            R.layout.show_detail_fragment,
            container,
            false
        ).apply {
            viewModel = showDetailViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

}