package com.example.testappprotei.presentation.photos

import android.util.Log
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

    private fun getPhotos(albumsId: Int?) {
        if (albumsId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = mainInteractor.getAlbumsPhoto(albumsId)
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        _uiState.value = _uiState.value.copy(photos = it.toPhotosState())
                        insertPhotosDb(it.toPhotosEntity())
                    }
                } else {
                    // TODO: add error state
                }
            }

        } else {
            // TODO: add error state
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
                _uiState.value.isLoading = true
                val photos = photosRepo.getAllPhotosData(id)
                if (photos.isNotEmpty()) {
                    _uiState.value =
                        _uiState.value.copy(photos = photos.toPhotosStateDb(), isLoading = false)
                } else {
                    getPhotos(id)
                }
            }
        } else {
            // TODO add error
        }
    }

    fun deletePhotoDb(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val dd = photosRepo.removePhotosDataById(id)
            Log.d("11111111111111111111111111", dd.toString())
            _uiState.value = _uiState.value.copy(photos = _uiState.value.photos.filter { photo -> photo?.id != id })
        }
    }
}