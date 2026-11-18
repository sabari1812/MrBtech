package com.example.mrbtech.domain.repository

import com.example.mrbtech.domain.model.ImportantUnit

interface NotesRepository {

    suspend fun getImportantUnits(
        department: String,
        semester: String,
        subjectName: String
    ): List<ImportantUnit>
}