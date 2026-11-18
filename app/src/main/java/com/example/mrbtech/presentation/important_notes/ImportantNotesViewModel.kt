package com.example.mrbtech.presentation.important_notes

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ImportantNotesViewModel @Inject constructor() : ViewModel() {

    private val _uiState =
        MutableStateFlow<ImportantNotesUIState>(
            ImportantNotesUIState.Loading
        )

    val uiState = _uiState.asStateFlow()

    init {
        loadSemesters()
    }

    private fun loadSemesters() {
        _uiState.value = ImportantNotesUIState.Success(
            semesters = (1..8).toList()
        )
    }
}