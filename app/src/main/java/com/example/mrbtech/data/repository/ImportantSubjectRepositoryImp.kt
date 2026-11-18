package com.example.mrbtech.data.repository


import com.example.mrbtech.domain.model.ImportantSubject
import com.example.mrbtech.domain.repository.ImportantSubjectRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ImportantSubjectRepositoryImp @Inject constructor(
    private val firestore: FirebaseFirestore
): ImportantSubjectRepository {
    override suspend fun getImportantSubjects(
        department: String,
        semester: String
    ): List<ImportantSubject> {
        val result = firestore
            .collection("departments")
            .document(department)
            .collection("importantNotes")
            .document(semester)
            .collection("subjects")
            .get()
            .await()

        return result.documents.mapNotNull { document ->

            document.getString("subjectName")?.let { name ->

                ImportantSubject(
                    subject = document.id,
                    subjectName = name
                )
            }
        }
    }
}