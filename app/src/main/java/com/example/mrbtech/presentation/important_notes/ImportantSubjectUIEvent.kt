package com.example.mrbtech.presentation.important_notes

sealed class ImportantSubjectUIEvent {

    data class Navigate(
        val route: String
    ) : ImportantSubjectUIEvent()
}