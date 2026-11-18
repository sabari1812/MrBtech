package com.example.mrbtech.presentation.downloaded_pdf

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mrbtech.domain.model.DownloadedPdf
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DownloadsScreen(
    viewModel: DownloadViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val filteredFiles = uiState.files.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FC))
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF20BDF5),
                            Color(0xFF0759ED)
                        )
                    )
                )
                .padding(20.dp)
                .statusBarsPadding()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Column {
                    Text(
                        text = "Downloaded Files",
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "All your downloaded notes in one place",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 13.sp
                    )
                }
            }
        }
        Text(
            text = "All Downloads (${filteredFiles.size})",
            modifier = Modifier.padding(
                horizontal = 20.dp,
                vertical = 8.dp
            ),
            color = Color(0xFF514FDE),
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp
        )

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                Text(
                    text = uiState.error ?: "Something went wrong",
                    modifier = Modifier.padding(20.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }

            filteredFiles.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.FolderOpen,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color(0xFF8B8FA8)
                        )

                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = if (searchQuery.isBlank())
                                "No downloaded PDFs yet"
                            else
                                "No matching files found",
                            color = Color.Gray
                        )
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 24.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = filteredFiles,
                        key = { it.id }
                    ) { file ->
                        DownloadedFileCard(
                            file = file,
                            onOpen = {
                                openDownloadedPdf(context, file.filePath)
                            },
                            onDelete = {
                                viewModel.deleteFile(file.filePath)
                            }
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun DownloadedFileCard(
    file: DownloadedPdf,
    onOpen: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onOpen),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(62.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFF0EDFF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.PictureAsPdf,
                    contentDescription = "PDF",
                    tint = Color(0xFF6255D9),
                    modifier = Modifier.size(34.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = file.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color(0xFF252D49)
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = formatFileSize(file.sizeByte),
                    fontSize = 13.sp,
                    color = Color(0xFF7C849F)
                )

                Text(
                    text = formatDownloadDate(file.downloadedAt),
                    fontSize = 12.sp,
                    color = Color(0xFF7C849F)
                )
            }

            IconButton(onClick = onOpen) {
                Icon(
                    Icons.Default.Visibility,
                    contentDescription = "Open PDF",
                    tint = Color(0xFF514FDE)
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.DeleteOutline,
                    contentDescription = "Delete download",
                    tint = Color(0xFFE34D59)
                )
            }
        }
    }
}

private fun formatFileSize(bytes: Long): String {
    return if (bytes < 1024 * 1024) {
        "%.1f KB".format(bytes / 1024.0)
    } else {
        "%.1f MB".format(bytes / (1024.0 * 1024.0))
    }
}

private fun formatDownloadDate(timestamp: Long): String {
    if (timestamp <= 0L) return "Download date unavailable"

    return SimpleDateFormat(
        "dd MMM yyyy",
        Locale.getDefault()
    ).format(Date(timestamp))
}
private fun openDownloadedPdf(
    context: Context,
    filePath: String
) {

    val file = File(filePath)

    if (!file.exists()) {
        Toast.makeText(
            context,
            "PDF file not found",
            Toast.LENGTH_SHORT
        ).show()
        return
    }

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(
            context,
            "Please install a PDF viewer",
            Toast.LENGTH_SHORT
        ).show()
    }
}