package com.example.mrbtech.presentation.downloaded_pdf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mrbtech.domain.repository.DownloadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject




@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val repository: DownloadRepository
): ViewModel(){

    private val _uiState = MutableStateFlow(DownloadsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadFiles()
    }
    fun loadFiles(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                val files = withContext(Dispatchers.IO){
                    repository.getDownloadFile()
                }
                _uiState.update {
                    it.copy(isLoading = false, files = files)
                }
            }catch (e: Exception){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unable to load downloads"
                    )
                }
            }
        }
    }
    fun deleteFile(filePath: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.removePdf(filePath)
                }
                loadFiles()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Unable to delete file"
                    )
                }
            }
        }
    }
}
