package com.example.mrbtech.presentation.question_paper

import com.example.mrbtech.domain.model.QuestionPaperSubject

sealed class QuestionPaperSubjectUIState {

    object Loading : QuestionPaperSubjectUIState()

    data class Error(
        val msg: String
    ) : QuestionPaperSubjectUIState()

    data class Success(
        val subjects: List<QuestionPaperSubject>
    ) : QuestionPaperSubjectUIState()
}