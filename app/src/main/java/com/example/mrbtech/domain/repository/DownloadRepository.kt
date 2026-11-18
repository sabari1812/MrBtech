package com.example.mrbtech.domain.repository

import com.example.mrbtech.domain.model.DownloadedPdf

interface DownloadRepository {

    fun getDownloadFile(): List<DownloadedPdf>
    fun removePdf(filePath: String)
}