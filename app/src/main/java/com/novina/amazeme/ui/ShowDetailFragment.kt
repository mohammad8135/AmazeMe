package com.novina.amazeme.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.novina.amazeme.R
import com.novina.amazeme.databinding.FragmentShowDetailBinding
import com.novina.amazeme.ui.viewmodel.ShowDetailViewModel
import com.novina.amazeme.ui.viewmodel.ShowDetailViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShowDetailFragment : Fragment() {

    @Inject
    lateinit var showDetailViewModelFactory: ShowDetailViewModelFactory
    private val args: ShowDetailFragmentArgs by navArgs()
    private val showDetailViewModel: ShowDetailViewModel by viewModels {
        ShowDetailViewModel.provideFactory(showDetailViewModelFactory, args.showId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentShowDetailBinding>(
            inflater,
            R.layout.fragment_show_detail,
            container,
            false
        ).apply {
            viewModel = showDetailViewModel
            lifecycleOwner = viewLifecycleOwner

            var isToolbarShown = false

            // scroll change listener begins at Y = 0 when image is fully collapsed
            showDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                    // User scrolled past image to height of toolbar and the title text is
                    // underneath the toolbar, so the toolbar should be shown.
                    val shouldShowToolbar = scrollY > toolbar.height

                    // The new state of the toolbar differs from the previous state; update
                    // appbar and toolbar attributes.
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar

                        // Use shadow animator to add elevation if toolbar is shown
                        appbar.isActivated = shouldShowToolbar

                        // Show the show name if toolbar is shown
                        toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                }
            )

            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        return binding.root
    }

}