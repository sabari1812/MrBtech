package com.example.mrbtech.data.repository

import android.content.Context
import android.os.Environment
import com.example.mrbtech.domain.model.DownloadedPdf
import com.example.mrbtech.domain.repository.DownloadRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class DownloadRepositoryImp @Inject constructor(
    @ApplicationContext private val context: Context
) : DownloadRepository {

    override fun getDownloadFile(): List<DownloadedPdf> {

        val directory = context.getExternalFilesDir(
            Environment.DIRECTORY_DOWNLOADS
        )

        return directory
            ?.listFiles()
            ?.filter { file ->
                file.isFile &&
                        file.extension.equals("pdf", ignoreCase = true)
            }
            ?.map { file ->
                DownloadedPdf(
                    id = file.absolutePath,
                    title = file.name,
                    sizeByte = file.length(),
                    downloadedAt = file.lastModified(),
                    filePath = file.absolutePath
                )
            }
            ?.sortedByDescending { it.downloadedAt }
            .orEmpty()
    }

    override fun removePdf(filePath: String) {
        File(filePath).delete()
    }
}