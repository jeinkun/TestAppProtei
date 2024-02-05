package com.example.testappprotei.presentation.albums

import android.util.Log
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

    fun getAlbums(userId: Int?) {
        if (userId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val response = mainInteractor.getUsersAlbums(userId)
                _uiState.value = _uiState.value.copy(isLoading = false, isError = false)
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        _uiState.value = _uiState.value.copy(albums = it.toAlbumsState())
                        insertAlbumsDb(it.toAlbumsEntity())
                    }
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false, isError = true)
                }
            }

        } else {
            _uiState.value = _uiState.value.copy(isLoading = false, isError = true)
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
                _uiState.value = _uiState.value.copy(isLoading = true)
                val albums = albumsRepo.getAllAlbumsData(id)
                if (albums.isNotEmpty()) {
                    _uiState.value =
                        _uiState.value.copy(albums = albums.toAlbumsStateDb(), isLoading = false, isError = false)
                } else {
                    getAlbums(id)
                }
            }
        } else {
            _uiState.value = _uiState.value.copy(isLoading = false, isError = true)
        }
    }

    fun deleteAlbumDb(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            albumsRepo.removeAlbumsDataById(id)
            _uiState.value =
                _uiState.value.copy(albums = _uiState.value.albums.filter { album -> album?.id != id })
        }
    }

    fun updateFavoriteAlbum(isFavorite: Boolean, idAlbum: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            albumsRepo.updateFavoriteAlbum(isFavorite, idAlbum)
            _uiState.value =
                _uiState.value.copy(albums = _uiState.value.albums.map {
                    if (it?.id == idAlbum) it.copy(favorite = isFavorite) else it
                })
        }
    }

}