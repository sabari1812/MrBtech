package com.example.mrbtech.data.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.example.mrbtech.domain.repository.Downloader
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject



class AndroidDownloader @Inject constructor(
    @ApplicationContext private val context: Context
): Downloader{
    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    override fun downloadFile(url: String,name:String,unit: Int): Long {
        val fileName = "$name$unit.pdf"
        val request = DownloadManager.Request(url.toUri())
            .setTitle(name+" Unit ${unit}")
            .setDescription("Downloading PDF notes...")
            .setMimeType("application/pdf")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalFilesDir(context,Environment.DIRECTORY_DOWNLOADS,fileName)
        return downloadManager.enqueue(request)
    }

    override fun downloadQuestionPaper(url: String, name: String): Long {
        val fileName = "${name.replace(" ", "_")}_QuestionPapers.pdf"

        val request = DownloadManager.Request(url.toUri())
            .setTitle("$name Question Papers")
            .setDescription("Downloading question papers...")
            .setMimeType("application/pdf")
            .setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            )
            .setDestinationInExternalFilesDir(
                context,
                Environment.DIRECTORY_DOWNLOADS,
                fileName
            )

        return downloadManager.enqueue(request)
    }
}