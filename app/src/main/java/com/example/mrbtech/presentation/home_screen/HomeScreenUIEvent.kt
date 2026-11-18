package com.example.mrbtech.presentation.home_screen

sealed class HomeScreenUIEvent {

    data class NavigationHomeScreen(val route: String): HomeScreenUIEvent()
}