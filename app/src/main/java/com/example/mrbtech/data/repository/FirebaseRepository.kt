package com.example.mrbtech.data.repository

import com.example.mrbtech.data.remote.FirestoreDataSource
import com.example.mrbtech.domain.model.Subject
import com.example.mrbtech.domain.model.Unit
import com.example.mrbtech.domain.repository.SubjectRepository
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val dataSource: FirestoreDataSource
): SubjectRepository {
    override suspend fun getSubjects(
        department: String,
        semester: String
    ): List<Subject> {
        return dataSource.getSubjects(department, semester)
    }

    override suspend fun getUnits(
        department: String,
        semester: String,
        subjectName: String
    ): List<Unit> {
        return dataSource.getUnits(
            department,
            semester,
            subjectName
        )
    }
}