package com.example.mrbtech.presentation.login_screen

sealed class LoginScreenUiEvent {
    data class SelectedDepartment(val dept: String): LoginScreenUiEvent()
    data class ShowSnackbar(val msg:String): LoginScreenUiEvent()
    data class Navigate(val route: String): LoginScreenUiEvent()
    object Login: LoginScreenUiEvent()
}