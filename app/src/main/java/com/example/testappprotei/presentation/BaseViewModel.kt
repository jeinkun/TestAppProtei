package com.example.testappprotei.presentation

import androidx.lifecycle.ViewModel
import com.example.testappprotei.repository.MainInteractor

open class BaseViewModel : ViewModel() {

    protected val mainInteractor = MainInteractor()
}