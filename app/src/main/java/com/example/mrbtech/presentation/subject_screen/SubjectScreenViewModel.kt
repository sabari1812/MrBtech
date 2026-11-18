package com.example.mrbtech.presentation.subject_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mrbtech.domain.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SubjectScreenViewModel @Inject constructor(
    private val repository: SubjectRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<SubjectScreenUIState>(SubjectScreenUIState.Loading)
    val uiState = _uiState.asStateFlow()
    private val _uiEvent = Channel<SubjectUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: SubjectUIEvent){
        when(event){
            is SubjectUIEvent.Navigate -> {
                viewModelScope.launch {
                    _uiEvent.send(SubjectUIEvent.Navigate(event.route))
                }
            }
        }
    }
    fun loadSubject(
        department: String,
        semester: String
    ){
        viewModelScope.launch {
            _uiState.value = SubjectScreenUIState.Loading
            try {

                val subjects = repository.getSubjects(
                    department,
                    semester
                )
                Log.d("SubjectDebug", "Department: $department")
                Log.d("SubjectDebug", "Semester: $semester")
                Log.d("SubjectDebug", "Subjects: $subjects")
                Log.d("SubjectDebug", "Count: ${subjects.size}")
                _uiState.value = SubjectScreenUIState.Success(subjects)
            }catch (e: Exception){
                _uiState.value = SubjectScreenUIState.Error(e.message.toString())
            }
        }
    }
}