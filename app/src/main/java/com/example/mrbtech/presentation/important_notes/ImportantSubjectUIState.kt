package com.example.mrbtech.presentation.important_notes

import com.example.mrbtech.domain.model.ImportantSubject

sealed class ImportantSubjectUIState {

    object Loading : ImportantSubjectUIState()

    data class Error(
        val msg: String
    ) : ImportantSubjectUIState()

    data class Success(
        val subjects: List<ImportantSubject>
    ) : ImportantSubjectUIState()
}