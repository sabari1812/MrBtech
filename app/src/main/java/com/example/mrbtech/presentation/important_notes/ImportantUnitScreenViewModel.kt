package com.example.mrbtech.presentation.important_notes


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mrbtech.domain.repository.NotesRepository
import com.example.mrbtech.domain.repository.Downloader
import com.example.mrbtech.data.util.GoogleDriveUrlConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImportantUnitScreenViewModel @Inject constructor(
    private val repository: NotesRepository,
    private val downloader: Downloader
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ImportantUnitUIState>(
            ImportantUnitUIState.Loading
        )

    val uiState = _uiState.asStateFlow()

    fun loadUnits(
        department: String,
        semester: String,
        subjectName: String
    ) {
        viewModelScope.launch {

            _uiState.value = ImportantUnitUIState.Loading

            try {
                val units = repository.getImportantUnits(
                    department = department,
                    semester = semester,
                    subjectName = subjectName
                )

                _uiState.value =
                    ImportantUnitUIState.Success(units)

            } catch (e: Exception) {
                _uiState.value =
                    ImportantUnitUIState.Error(
                        e.message ?: "Something went wrong"
                    )
            }
        }
    }

    fun onEvent(event: ImportantUnitUIEvent) {

        when (event) {

            is ImportantUnitUIEvent.DownloadFile -> {

                val directUrl = GoogleDriveUrlConverter.convertToDirectDownloadUrl(event.url)

                if (directUrl != null) {
                    downloader.downloadFile(
                        url = directUrl,
                        name = event.name,
                        unit = event.unit
                    )
                }
            }
        }
    }
}
