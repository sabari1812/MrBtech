package com.example.mrbtech.domain.model

data class DownloadedPdf(
    val id: String,
    val title: String,
    val sizeByte: Long,
    val downloadedAt: Long,
    val filePath: String
)
