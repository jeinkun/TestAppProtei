package com.example.testappprotei.presentation.albums

import androidx.lifecycle.viewModelScope
import com.example.testappprotei.dataBase.Dependencies
import com.example.testappprotei.dataBase.model.AlbumsEntity
import com.example.testappprotei.presentation.BaseViewModel
import com.example.testappprotei.presentation.mappers.toAlbumsEntity
import com.example.testappprotei.presentation.mappers.toAlbumsState
import com.example.testappprotei.presentation.mappers.toAlbumsStateDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlbumsViewModel : BaseViewModel() {

    private val _uiState = MutableStateFlow(AlbumsState())
    val uiState: StateFlow<AlbumsState> = _uiState.asStateFlow()
    private val albumsRepo = Dependencies.albumsDbRepo

    private fun getAlbums(userId: Int?) {
        if (userId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = mainInteractor.getUsersAlbums(userId)
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        _uiState.value = _uiState.value.copy(albums = it.toAlbumsState())
                        insertAlbumsDb(it.toAlbumsEntity())
                    }
                } else {
                    // TODO: add error state
                }
            }

        } else {
            // TODO: add error state
        }
    }

    private fun insertAlbumsDb(albums: List<AlbumsEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            albumsRepo.insertNewAlbumsData(albums)
        }
    }

    fun getAlbumsDb(id: Int?) {
        if (id != null) {
            viewModelScope.launch(Dispatchers.IO) {
                _uiState.value.isLoading = true
                val albums = albumsRepo.getAllAlbumsData(id)
                if (albums.isNotEmpty()) {
                    _uiState.value =
                        _uiState.value.copy(albums = albums.toAlbumsStateDb(), isLoading = false)
                } else {
                    getAlbums(id)
                }
            }
        } else {
            // TODO add error
        }
    }

    fun deleteAlbumDb(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            albumsRepo.removeAlbumsDataById(id)
            _uiState.value = _uiState.value.copy(albums = _uiState.value.albums.filter { album -> album?.id != id })
        }
    }

}