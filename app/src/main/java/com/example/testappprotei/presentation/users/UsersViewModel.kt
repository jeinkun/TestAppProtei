package com.example.testappprotei.presentation.users

import androidx.lifecycle.viewModelScope
import com.example.testappprotei.repository.dataBase.Dependencies
import com.example.testappprotei.repository.dataBase.model.UsersEntity
import com.example.testappprotei.presentation.BaseViewModel
import com.example.testappprotei.presentation.mappers.toUsersEntity
import com.example.testappprotei.presentation.mappers.toUsersState
import com.example.testappprotei.presentation.mappers.toUsersStateDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel : BaseViewModel() {

    private val _uiState = MutableStateFlow(UsersState())
    val uiState: StateFlow<UsersState> = _uiState.asStateFlow()
    private val userRepo = Dependencies.usersDbRepo

    init {
        viewModelScope.launch {
            userRepo.getAllUsersData().collect {
                _uiState.value = _uiState.value.copy(users = it.toUsersStateDb())
            }
        }
    }

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val response = mainInteractor.getUsers()
            if (response.isSuccessful) {
                val result = response.body()
                result?.let {
                    _uiState.value = _uiState.value.copy(users = it.toUsersState(), isLoading = false, isError = false)
                    insertUsersDb(it.toUsersEntity())
                }
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, isError = true)
            }
        }
    }

    private fun insertUsersDb(users: List<UsersEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.insertNewUsersData(users)
        }
    }

    private fun getUsersDb() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            userRepo.getAllUsersData()
        }
    }

    fun deleteUserDb(id: Int) {
        viewModelScope.launch {
            userRepo.removeUsersDataById(id)
            _uiState.value = _uiState.value.copy(users = _uiState.value.users.filter { user -> user?.id != id })
        }
    }
}