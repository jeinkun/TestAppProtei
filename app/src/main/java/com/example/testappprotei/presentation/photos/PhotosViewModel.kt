package com.example.testappprotei.presentation.photos

import androidx.lifecycle.viewModelScope
import com.example.testappprotei.repository.dataBase.Dependencies
import com.example.testappprotei.repository.dataBase.model.PhotosEntity
import com.example.testappprotei.presentation.BaseViewModel
import com.example.testappprotei.presentation.mappers.toAlbumsStateDb
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
                photosRepo.getAllPhotosData(albumsId).collect {
                    _uiState.value = _uiState.value.copy(photos = it.toPhotosStateDb())
                }
            }

        } else {
            _uiState.value = _uiState.value.copy(isLoading = false, isError = true)
        }
    }

    fun getPhotosUpdate(albumsId: Int?) {
        if (albumsId != null) {
            viewModelScope.launch{
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

    private fun insertPhotosDb(data: List<PhotosEntity>) {
        viewModelScope.launch {
            photosRepo.insertNewPhotosData(data)
        }
    }

    fun deletePhotoDb(id: Int) {
        viewModelScope.launch {
            photosRepo.removePhotosDataById(id)
            _uiState.value = _uiState.value.copy(photos = _uiState.value.photos.filter { photo -> photo?.id != id })
        }
    }
}