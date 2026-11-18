package com.example.mrbtech.presentation.important_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mrbtech.domain.repository.ImportantSubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImportantSubjectViewModel @Inject constructor(
    private val repository: ImportantSubjectRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ImportantSubjectUIState>(
            ImportantSubjectUIState.Loading
        )

    val uiState = _uiState.asStateFlow()

    private val _uiEvent =
        Channel<ImportantSubjectUIEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ImportantSubjectUIEvent) {

        when (event) {

            is ImportantSubjectUIEvent.Navigate -> {

                viewModelScope.launch {
                    _uiEvent.send(event)
                }
            }
        }
    }

    fun loadSubjects(
        department: String,
        semester: String
    ) {

        viewModelScope.launch {

            _uiState.value = ImportantSubjectUIState.Loading

            try {

                val subjects = repository.getImportantSubjects(
                    department = department,
                    semester = semester
                )

                _uiState.value =
                    ImportantSubjectUIState.Success(subjects)

            } catch (e: Exception) {

                _uiState.value =
                    ImportantSubjectUIState.Error(
                        e.message ?: "Something went wrong"
                    )
            }
        }
    }
}