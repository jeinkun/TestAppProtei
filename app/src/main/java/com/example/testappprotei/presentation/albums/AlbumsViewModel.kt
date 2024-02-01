package com.example.testappprotei.presentation.albums

import androidx.lifecycle.viewModelScope
import com.example.testappprotei.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlbumsViewModel : BaseViewModel() {

    private val _uiState = MutableStateFlow(AlbumsState())
    val uiState: StateFlow<AlbumsState> = _uiState.asStateFlow()

    init {
        getAlbums()
    }

    private fun getAlbums() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = mainInteractor.getUsersAlbums(1)
            if (response.isSuccessful) {
                val result = response.body()
                result?.let {
                    _uiState.value = _uiState.value.copy(albums = it.toAlbumsState())
                }
            } else {
                // TODO: add error state
            }
        }
    }
}