package com.example.mrbtech.presentation.notes_screen

sealed class NotesScreenUIEvent {
    data class NavigateScreen(val route: String): NotesScreenUIEvent()
}