package com.example.mrbtech.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(): ViewModel() {

    private val _uiEvent = Channel<HomeScreenUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: HomeScreenUIEvent){
        when(event){
            is HomeScreenUIEvent.NavigationHomeScreen -> {
                viewModelScope.launch {
                    _uiEvent.send(HomeScreenUIEvent.NavigationHomeScreen(event.route))
                }
            }
        }
    }
}