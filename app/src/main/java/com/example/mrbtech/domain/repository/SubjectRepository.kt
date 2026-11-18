package com.example.mrbtech.domain.repository

import com.example.mrbtech.domain.model.Subject
import com.example.mrbtech.domain.model.Unit

interface SubjectRepository {

    suspend fun getSubjects(
        department: String,
        semester: String
    ): List<Subject>

    suspend fun getUnits(
        department: String,
        semester: String,
        subjectName: String
    ): List<Unit>
}