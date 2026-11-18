package com.example.mrbtech.data.remote

import android.util.Log
import com.example.mrbtech.domain.model.Subject
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.example.mrbtech.domain.model.Unit
class FirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun getSubjects(
        department: String,
        semester: String
    ): List<Subject>{
        val result = firestore
            .collection("departments")
            .document(department)
            .collection("notes")
            .document(semester)
            .collection("subjects")
            .get()
            .await()
        return result.documents.mapNotNull { document->
            document.getString("subjectName")?.let {subjectName->
                Subject(subject = document.id, subjectName = subjectName )
            }
        }
    }
    suspend fun getUnits(
        department: String,
        semester: String,
        subjectName: String
    ):List<Unit>{
        val result = firestore
            .collection("departments")
            .document(department)
            .collection("notes")
            .document(semester)
            .collection("subjects")
            .document(subjectName)
            .collection("units")
            .get()
            .await()
        return result.documents.mapNotNull { document->
            val unitNumber = document.getLong("unitNumber")?.toInt()
            val url = document.getString("pdfUrl")
            Log.d("MainActivityLog",url+unitNumber)
            if (unitNumber!= null && url!=null){
                Unit(unitNumber, url)
            }else null
        }
    }
}