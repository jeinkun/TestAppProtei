package com.example.testappprotei.presentation.albums

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.testappprotei.repository.dataBase.Dependencies
import com.example.testappprotei.repository.dataBase.model.AlbumsEntity
import com.example.testappprotei.presentation.BaseViewModel
import com.example.testappprotei.presentation.mappers.toAlbumsEntity
import com.example.testappprotei.presentation.mappers.toAlbumsState
import com.example.testappprotei.presentation.mappers.toAlbumsStateDb
import com.example.testappprotei.presentation.mappers.toUsersStateDb
import com.example.testappprotei.repository.dataBase.model.PhotosEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlbumsViewModel : BaseViewModel() {

    private val _uiState = MutableStateFlow(AlbumsState())
    val uiState: StateFlow<AlbumsState> = _uiState
    private val albumsRepo = Dependencies.albumsDbRepo

    fun getAlbums(userId: Int?) {
        viewModelScope.launch {
            if (userId != null) {
                albumsRepo.getAllAlbumsData(userId).collect {
                    _uiState.value = _uiState.value.copy(albums = it.toAlbumsStateDb())
                }
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, isError = true)
            }
        }
    }

    fun getAlbumsUpdate(id: Int?) {

        viewModelScope.launch(Dispatchers.IO) {
            if (id != null) {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val response = mainInteractor.getUsersAlbums(id)
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
            } else {

                _uiState.value = _uiState.value.copy(isLoading = false, isError = true)
            }

        }
    }

    fun deleteAlbumDb(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            albumsRepo.removeAlbumsDataById(id)
            _uiState.value =
                _uiState.value.copy(albums = _uiState.value.albums.filter { album -> album?.id != id })
        }
    }

    private fun insertAlbumsDb(data: List<AlbumsEntity>) {
        viewModelScope.launch {
            albumsRepo.insertNewAlbumsData(data)
        }
    }

    fun updateFavoriteAlbum(isFavorite: Boolean, idAlbum: Int) {
        viewModelScope.launch {
            albumsRepo.updateFavoriteAlbum(isFavorite, idAlbum)
            _uiState.value = _uiState.value.copy(albums = _uiState.value.albums.map {
                if (it?.id == idAlbum) it.copy(favorite = isFavorite) else it
            })
        }
    }

}