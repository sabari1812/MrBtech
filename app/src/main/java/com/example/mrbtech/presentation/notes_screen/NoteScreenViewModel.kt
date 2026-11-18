package com.example.mrbtech.presentation.notes_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mrbtech.presentation.notes_screen.NotesScreenUIEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteScreenViewModel @Inject constructor(): ViewModel() {


    private val _uiEvent = Channel<NotesScreenUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NotesScreenUIEvent){
        when(event){
            is NotesScreenUIEvent.NavigateScreen -> {
                viewModelScope.launch {
                _uiEvent.send(NavigateScreen(event.route))
                }
            }
        }
    }
}