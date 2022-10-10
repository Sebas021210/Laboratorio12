package com.example.laboratorio12

import android.media.Image
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow<UiState>(UiState.Default)
    val uiState: StateFlow<UiState> = _uiState

    sealed class UiState {
        object Default : UiState()
        object Success : UiState()
        object Failure : UiState()
        object Empty : UiState()
        object Loading : UiState()
    }

    fun default() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            delay(1000L)
            _uiState.value = UiState.Default
        }

    }

    fun success() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            delay(1000L)
            _uiState.value = UiState.Success
        }
    }

    fun failure() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            delay(1000L)
            _uiState.value = UiState.Failure
        }
    }

    fun empty() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            delay(1000L)
            _uiState.value = UiState.Empty
        }
    }

}