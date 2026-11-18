package com.example.mrbtech.data.repository

import com.example.mrbtech.domain.model.ImportantUnit
import com.example.mrbtech.domain.repository.NotesRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ImportantNotesRepositoryimp @Inject constructor(
    private val firestore: FirebaseFirestore
): NotesRepository {
    override suspend fun getImportantUnits(
        department: String,
        semester: String,
        subjectName: String
    ): List<ImportantUnit> {
        val result = firestore
            .collection("departments")
            .document(department)
            .collection("importantNotes")
            .document(semester)
            .collection("subjects")
            .document(subjectName)
            .collection("units")
            .get()
            .await()

        return result.documents.mapNotNull { document ->

            val unitNumber =
                document.getLong("unitNumber")?.toInt()

            val pdfUrl =
                document.getString("pdfUrl")

            if (unitNumber != null && pdfUrl != null) {
                ImportantUnit(
                    unitNumber = unitNumber,
                    pdfUrl = pdfUrl
                )
            } else {
                null
            }

        }.sortedBy { it.unitNumber }
    }

}