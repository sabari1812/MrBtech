package com.example.mrbtech.domain.repository

import com.example.mrbtech.domain.model.ImportantSubject

interface ImportantSubjectRepository {

    suspend fun getImportantSubjects(
        department: String,
        semester: String
    ): List<ImportantSubject>
}