package com.novina.amazeme.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.novina.amazeme.data.model.Show
import com.novina.amazeme.data.repository.ShowsRepository
import com.novina.amazeme.domain.LoadShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ShowListViewModel @Inject constructor(
    showsRepository: ShowsRepository
) : ViewModel() {

    val state: StateFlow<ShowsUiState> = LoadShowsUseCase(showsRepository).invoke(1)
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