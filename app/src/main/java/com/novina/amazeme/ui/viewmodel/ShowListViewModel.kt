package com.novina.amazeme.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.novina.amazeme.domain.LoadShowsUseCase
import com.novina.amazeme.model.Show
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ShowListViewModel @Inject constructor(
    loadShowsUseCase: LoadShowsUseCase
) : ViewModel() {

    val state: StateFlow<ShowsUiState> = loadShowsUseCase.invoke(1)
        .cachedIn(viewModelScope)
        .map { pagingData: PagingData<Show> -> ShowsUiState(pagingData) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = ShowsUiState()
        )
}

data class ShowsUiState(
    val pagingData: PagingData<Show> = PagingData.empty()

)