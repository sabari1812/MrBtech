package com.example.mrbtech.domain.repository

interface Downloader {
    fun downloadFile(url: String,name: String,unit: Int): Long
    fun downloadQuestionPaper(
        url: String,
        name: String
    ): Long
}