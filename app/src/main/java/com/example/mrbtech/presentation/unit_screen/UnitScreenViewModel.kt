package com.example.mrbtech.presentation.unit_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mrbtech.data.repository.FirebaseRepository
import com.example.mrbtech.domain.repository.Downloader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.mrbtech.data.util.GoogleDriveUrlConverter
import com.example.mrbtech.domain.repository.SubjectRepository

@HiltViewModel
class UnitScreenViewModel @Inject constructor(
    private val repository: SubjectRepository,
    private val downloader: Downloader
): ViewModel() {

    private val _uiState = MutableStateFlow<UnitScreenUIState>(UnitScreenUIState.Loading)
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: UnitScreenUIEvent) {
        when(event){
            is UnitScreenUIEvent.DownloadFile ->{
                val directUrl = GoogleDriveUrlConverter.convertToDirectDownloadUrl(event.url)
                if(directUrl!= null && event.unit!= null && event.name!=null)
              downloader.downloadFile(directUrl,event.name,event.unit)
            }
           else -> null
        }
    }
    fun loadUnits(
        department: String,
        semester: String,
        subjectName: String
    ){
        viewModelScope.launch {
            _uiState.value = UnitScreenUIState.Loading
            try {

               val units = repository.getUnits(department,semester,subjectName)
                _uiState.value = UnitScreenUIState.Success(units)
            }catch (e: Exception){
                _uiState.value = UnitScreenUIState.Error(e.message.toString())
            }
        }
    }
}