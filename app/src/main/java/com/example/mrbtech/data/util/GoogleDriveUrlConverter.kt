package com.example.mrbtech.data.util

object GoogleDriveUrlConverter {

    fun convertToDirectDownloadUrl(
        url: String
    ): String? {

        val fileId = Regex(
            """/file/d/([a-zA-Z0-9_-]+)"""
        ).find(url)?.groupValues?.get(1)

        return when {
            fileId != null ->
                "https://drive.google.com/uc?export=download&id=$fileId"

            url.startsWith("https://") -> url

            else -> null
        }
    }
}