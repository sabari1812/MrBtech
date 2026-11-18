package com.example.mrbtech.presentation.question_paper


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mrbtech.domain.repository.Downloader
import com.example.mrbtech.domain.repository.QuestionPaperRepository
import com.example.mrbtech.data.util.GoogleDriveUrlConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionPaperSubjectViewModel @Inject constructor(
    private val repository: QuestionPaperRepository,
    private val downloader: Downloader
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<QuestionPaperSubjectUIState>(
            QuestionPaperSubjectUIState.Loading
        )

    val uiState = _uiState.asStateFlow()

    fun loadSubjects(
        department: String,
        semester: String
    ) {
        viewModelScope.launch {

            _uiState.value = QuestionPaperSubjectUIState.Loading

            try {
                val subjects = repository.getQuestionPaperSubjects(
                    department = department,
                    semester = semester
                )

                _uiState.value =
                    QuestionPaperSubjectUIState.Success(subjects)

            } catch (e: Exception) {
                _uiState.value =
                    QuestionPaperSubjectUIState.Error(
                        e.message ?: "Something went wrong"
                    )
            }
        }
    }

    fun downloadQuestionPaper(
        subjectName: String,
        pdfUrl: String
    ) {
        val directUrl = GoogleDriveUrlConverter.convertToDirectDownloadUrl(pdfUrl)

        if (directUrl != null) {
            downloader.downloadQuestionPaper(
                url = directUrl,
                name = subjectName
            )
        }
    }

}