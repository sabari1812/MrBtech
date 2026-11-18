package com.example.mrbtech.presentation.subject_screen

sealed class SubjectUIEvent {
    data class Navigate(val route: String): SubjectUIEvent(

    )
}