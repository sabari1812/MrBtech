package com.example.mrbtech.presentation.important_notes

import com.example.mrbtech.domain.model.ImportantUnit

sealed class ImportantUnitUIState {

    object Loading : ImportantUnitUIState()

    data class Success(
        val units: List<ImportantUnit>
    ) : ImportantUnitUIState()

    data class Error(
        val message: String
    ) : ImportantUnitUIState()
}