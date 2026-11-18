package com.example.mrbtech.presentation.important_notes

sealed class ImportantNotesUIState {

    object Loading : ImportantNotesUIState()

    data class Success(
        val semesters: List<Int>
    ) : ImportantNotesUIState()
}