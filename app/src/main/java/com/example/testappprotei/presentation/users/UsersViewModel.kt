package com.example.testappprotei.presentation.users

import androidx.lifecycle.viewModelScope
import com.example.testappprotei.dataBase.Dependencies
import com.example.testappprotei.dataBase.model.UsersEntity
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
        getUsersDb()
    }

    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value.isLoading = true
            val response = mainInteractor.getUsers()
            _uiState.value.isLoading = false
            if (response.isSuccessful) {
                val result = response.body()
                result?.let {
                    _uiState.value = _uiState.value.copy(users = it.toUsersState())
                    insertUsersDb(it.toUsersEntity())
                }
            } else {
                // TODO: add error state
            }
        }
    }

    private fun insertUsersDb(users: List<UsersEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.insertNewUsersData(users)
        }
    }

    private fun getUsersDb() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value.isLoading = true
            val users = userRepo.getAllUsersData()
            if (users.isNotEmpty()) {
                _uiState.value = _uiState.value.copy(users = users.toUsersStateDb(), isLoading = false)
            } else {
                getUsers()
            }
        }
    }

    fun deleteUserDb(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.removeUsersDataById(id)
            _uiState.value = _uiState.value.copy(users = _uiState.value.users.filter { user -> user?.id != id })
        }
    }
}