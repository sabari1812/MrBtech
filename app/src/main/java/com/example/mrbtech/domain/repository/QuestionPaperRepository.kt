package com.example.mrbtech.domain.repository

import com.example.mrbtech.domain.model.QuestionPaperSubject

interface QuestionPaperRepository {

    suspend fun getQuestionPaperSubjects(
        department: String,
        semester: String
    ): List<QuestionPaperSubject>
}