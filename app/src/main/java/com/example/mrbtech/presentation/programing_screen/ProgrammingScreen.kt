package com.example.mrbtech.presentation.programing_screen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ProgrammingScreen(
    onLanguageClick: (String) -> Unit,
    onExploreClick: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val languages = listOf(
        "Java" to "Object Oriented Programming",
        "Python" to "Simple and powerful programming",
        "C" to "Programming fundamentals",
        "C++" to "Object Oriented Programming",
        "Kotlin" to "Modern Android development"
    )

    val filteredLanguages = languages.filter {
        it.first.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F9FF))
    ) {

        // App Bar Header (fixed, full width)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF20BDF5),
                            Color(0xFF0759ED)
                        )
                    ),
                )
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Column {
                Text(
                    text = "YOUR LEARNING SPACE",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Programming Hub",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Learn, practise and improve your coding skills.",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }

        // Scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {



            Spacer(modifier = Modifier.height(22.dp))

            // Programming Languages
            Text(
                text = "Learn a Language",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF183B63)
            )

            Text(
                text = "Choose a language to start learning",
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            filteredLanguages.forEach { language ->
                LanguageCard(
                    language = language.first,
                    description = language.second,
                    onClick = { onLanguageClick(language.first) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Explore More
            Text(
                text = "Explore More",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF183B63)
            )

            Spacer(modifier = Modifier.height(12.dp))

            ExploreCard(
                title = "Data Structures & Algorithms",
                description = "Learn arrays, strings, sorting and more.",
                icon = "📚",
                onClick = { onExploreClick("DSA") }
            )

            ExploreCard(
                title = "Programming Examples",
                description = "Source code, explanations and outputs.",
                icon = "💻",
                onClick = { onExploreClick("Examples") }
            )

            ExploreCard(
                title = "Practice & Quiz",
                description = "Test your programming knowledge.",
                icon = "📝",
                onClick = { onExploreClick("Quiz") }
            )

            ExploreCard(
                title = "Interview Preparation",
                description = "Frequently asked technical questions.",
                icon = "🎯",
                onClick = { onExploreClick("Interview") }
            )
        }
    }
}
@Composable
fun LanguageCard(
    language: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEAF4FF)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = language.take(1),
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = language,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF183B63)
                )

                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Open",
                tint = Color(0xFF1976D2),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
    @Composable
    fun ExploreCard(
        title: String,
        description: String,
        icon: String,
        onClick: () -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .clickable { onClick() },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = icon,
                    fontSize = 28.sp
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF183B63)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = description,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
@Composable
fun ProgrammingWebScreen(url: String) {

    AndroidView(
        modifier = Modifier.fillMaxSize(),

        factory = { context ->

            WebView(context).apply {

                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true

                webViewClient = WebViewClient()

                loadUrl(url)
            }
        }
    )
}