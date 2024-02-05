package com.example.testappprotei.presentation.photos

import androidx.lifecycle.viewModelScope
import com.example.testappprotei.dataBase.Dependencies
import com.example.testappprotei.dataBase.model.PhotosEntity
import com.example.testappprotei.presentation.BaseViewModel
import com.example.testappprotei.presentation.mappers.toPhotosEntity
import com.example.testappprotei.presentation.mappers.toPhotosState
import com.example.testappprotei.presentation.mappers.toPhotosStateDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PhotosViewModel : BaseViewModel() {
    private val _uiState = MutableStateFlow(PhotosState())
    val uiState: StateFlow<PhotosState> = _uiState.asStateFlow()
    private val photosRepo = Dependencies.photosDbRepo

    fun getPhotos(albumsId: Int?) {
        if (albumsId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val response = mainInteractor.getAlbumsPhoto(albumsId)
                if (response.isSuccessful) {
                    val result = response.body()
                    _uiState.value = _uiState.value.copy(isLoading = false, isError = false)
                    result?.let {
                        _uiState.value = _uiState.value.copy(photos = it.toPhotosState())
                        insertPhotosDb(it.toPhotosEntity())
                    }
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false, isError = true)
                }
            }

        } else {
            _uiState.value = _uiState.value.copy(isLoading = false, isError = true)
        }
    }

    private fun insertPhotosDb(photos: List<PhotosEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            photosRepo.insertNewPhotosData(photos)
        }
    }

    fun getPhotosDb(id: Int?) {
        if (id != null) {
            viewModelScope.launch(Dispatchers.IO) {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val photos = photosRepo.getAllPhotosData(id)
                if (photos.isNotEmpty()) {
                    _uiState.value =
                        _uiState.value.copy(photos = photos.toPhotosStateDb(), isLoading = false, isError = false)
                } else {
                    getPhotos(id)
                }
            }
        } else {
            _uiState.value = _uiState.value.copy(isLoading = false, isError = true)
        }
    }

    fun deletePhotoDb(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            photosRepo.removePhotosDataById(id)
            _uiState.value = _uiState.value.copy(photos = _uiState.value.photos.filter { photo -> photo?.id != id })
        }
    }
}