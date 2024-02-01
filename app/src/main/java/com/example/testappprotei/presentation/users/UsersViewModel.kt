package com.example.testappprotei.presentation.users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappprotei.network.MainInteractor
import com.example.testappprotei.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel : BaseViewModel() {

    private val _uiState = MutableStateFlow(UsersState())
    val uiState: StateFlow<UsersState> = _uiState.asStateFlow()


    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = mainInteractor.getUsers()
            if (response.isSuccessful) {
                val result = response.body()
                result?.let {
                    _uiState.value = _uiState.value.copy(users = it.toUsersState())
                }
            } else {
                // TODO: add error state
            }
        }
    }
}