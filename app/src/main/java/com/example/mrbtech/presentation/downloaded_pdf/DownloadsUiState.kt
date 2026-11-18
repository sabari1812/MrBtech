package com.example.mrbtech.presentation.downloaded_pdf

import com.example.mrbtech.domain.model.DownloadedPdf

data class DownloadsUiState(
    val isLoading: Boolean = false,
    val files: List<DownloadedPdf> = emptyList(),
    val error: String? = null
)
