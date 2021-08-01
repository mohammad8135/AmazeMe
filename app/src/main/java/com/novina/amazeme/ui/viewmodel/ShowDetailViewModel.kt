package com.novina.amazeme.ui.showdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.novina.amazeme.model.Result
import com.novina.amazeme.model.Show
import com.novina.amazeme.data.repository.ShowsRepository
import com.novina.amazeme.domain.GetShowUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.map

class ShowDetailViewModel @AssistedInject constructor(
    showsRepository: ShowsRepository, @Assisted private val showId: Int
) : ViewModel() {

    val state: LiveData<ShowDetailUiState> = GetShowUseCase(showsRepository).invoke(showId)
        .map { result: Result<Show> ->
            when (result) {
                is Result.Success -> ShowDetailUiState(result.data)
                is Result.Error -> ShowDetailUiState(null)
            }
        }.asLiveData()

    companion object {
        fun provideFactory(
            assistedFactory: ShowDetailViewModelFactory,
            showId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(showId) as T
            }
        }
    }
}

@AssistedFactory
interface ShowDetailViewModelFactory {
    fun create(showId: Int): ShowDetailViewModel
}

data class ShowDetailUiState(
    val show: Show?
)
