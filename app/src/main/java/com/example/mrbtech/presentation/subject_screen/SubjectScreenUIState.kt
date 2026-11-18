package com.example.mrbtech.presentation.subject_screen

import com.example.mrbtech.domain.model.Subject

sealed class SubjectScreenUIState{

    object Loading: SubjectScreenUIState()
    data class Error(val msg: String): SubjectScreenUIState()
    data class Success(val subjects: List<Subject>): SubjectScreenUIState()
}
