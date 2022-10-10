package com.example.laboratorio12

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _validAuthToken: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val validAuthToken: StateFlow<Boolean> = _validAuthToken

    fun triggerStateFlow(){
        viewModelScope.launch {
            _validAuthToken.value = true
            delay(30000L)
            _validAuthToken.value = false
        }
    }

    fun login(){
        _validAuthToken.value = true
    }

    fun logout(){
        _validAuthToken.value = false
    }
}