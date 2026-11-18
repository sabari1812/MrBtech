package com.example.mrbtech.presentation.important_notes

sealed class ImportantUnitUIEvent {

    data class DownloadFile(
        val url: String,
        val name: String,
        val unit: Int
    ) : ImportantUnitUIEvent()
}