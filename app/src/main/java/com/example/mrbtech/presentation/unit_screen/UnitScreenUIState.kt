package com.example.mrbtech.presentation.unit_screen

import com.example.mrbtech.domain.model.Unit

sealed class UnitScreenUIState {

    data object Loading : UnitScreenUIState()

    data class Error(
        val message: String
    ) : UnitScreenUIState()

    data class Success(
        val units: List<Unit>
    ) : UnitScreenUIState()
}