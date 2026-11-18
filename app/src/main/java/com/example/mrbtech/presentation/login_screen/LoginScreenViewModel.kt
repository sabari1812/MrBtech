package com.example.mrbtech.presentation.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mrbtech.presentation.login_screen.LoginScreenUiEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<LoginScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: LoginScreenUiEvent){
        when(event){
            is LoginScreenUiEvent.SelectedDepartment -> {
                _uiState.update {
                    it.copy(department = event.dept)
                }
            }

            is LoginScreenUiEvent.ShowSnackbar -> {
                viewModelScope.launch {
                    _uiEvent.send(ShowSnackbar(event.msg))
                }
            }

            LoginScreenUiEvent.Login -> {
                viewModelScope.launch {
                    _uiEvent.send(ShowSnackbar("You're login in ${uiState.value.department} department"))
                    _uiEvent.send(Navigate(uiState.value.department))
                }
            }
            else -> null
        }
    }
}