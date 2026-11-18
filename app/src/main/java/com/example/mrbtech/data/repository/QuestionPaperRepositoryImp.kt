package com.example.mrbtech.data.repository


import com.example.mrbtech.domain.model.QuestionPaperSubject
import com.example.mrbtech.domain.repository.QuestionPaperRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.text.get

class QuestionPaperRepositoryImp @Inject constructor(
    private val firestore: FirebaseFirestore
): QuestionPaperRepository {
    override suspend fun getQuestionPaperSubjects(
        department: String,
        semester: String
    ): List<QuestionPaperSubject> {
        val result = firestore
            .collection("departments")
            .document(department)
            .collection("questionPapers")
            .document(semester)
            .collection("subjects")
            .get()
            .await()

        return result.documents.mapNotNull { document ->

            val subjectName =
                document.getString("subjectName")

            val pdfUrl =
                document.getString("pdfUrl")

            if (subjectName != null && pdfUrl != null) {
                QuestionPaperSubject(
                    subject = document.id,
                    subjectName = subjectName,
                    pdfUrl = pdfUrl
                )
            } else {
                null
            }
        }
    }
}

