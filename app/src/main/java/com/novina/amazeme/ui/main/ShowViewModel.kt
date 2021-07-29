package com.novina.amazeme.ui.main

import androidx.lifecycle.ViewModel
import com.novina.amazeme.data.repository.ShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowViewModel @Inject constructor(private val repository: ShowsRepository) : ViewModel() {


}