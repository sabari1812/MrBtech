package com.example.mrbtech.presentation.important_notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mrbtech.R
import com.example.mrbtech.util.SubjectCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportantSubjectScreen(
    department: String,
    semester: String,
    onNavigate: (ImportantSubjectUIEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    viewModel: ImportantSubjectViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {

        viewModel.uiEvent.collect { event ->

            when (event) {

                is ImportantSubjectUIEvent.Navigate -> {
                    onNavigate(event)
                }
            }
        }
    }

    LaunchedEffect(department, semester) {

        viewModel.loadSubjects(
            department = department,
            semester = semester
        )
    }

    when (val state = uiState.value) {

        is ImportantSubjectUIState.Loading -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator(
                    color = Color(0xFF1172EC)
                )
            }
        }

        is ImportantSubjectUIState.Error -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Text(text = state.msg)
            }
        }

        is ImportantSubjectUIState.Success -> {

            Scaffold(

                topBar = {

                    TopAppBar(

                        title = {

                            Text(
                                text = "Important Subjects",
                                color = Color.White,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },

                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFF1172EC),
                            navigationIconContentColor = Color.White,
                            actionIconContentColor = Color.White
                        ),

                        navigationIcon = {

                            IconButton(
                                onClick = onPopBackStack
                            ) {

                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        },

                        actions = {

                            IconButton(
                                onClick = {}
                            ) {

                                Icon(
                                    imageVector = Icons.Default.FormatListNumbered,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }

            ) { paddingValues ->

                Column(

                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)

                ) {

                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Image(
                            painter = painterResource(
                                R.drawable.semester_background
                            ),
                            contentDescription = null
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),

                            verticalArrangement =
                                Arrangement.spacedBy(4.dp)
                        ) {

                            Text(
                                text = "Department",
                                color = Color(0xFF3C71B6)
                            )

                            Text(
                                text = department,
                                color = Color(0xFF14315C),
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = semester.replaceFirstChar {
                                    it.uppercase()
                                },
                                color = Color(0xFF3C71B6)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Subjects",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF124D97)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (state.subjects.isEmpty()) {

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {

                            Text("No important subjects available")
                        }

                    } else {

                        androidx.compose.foundation.lazy.LazyColumn {

                            items(state.subjects.size) { index ->

                                val subject = state.subjects[index]

                                SubjectCard(
                                    subjectName = subject.subjectName,

                                    onClick = {

                                        viewModel.onEvent(
                                            ImportantSubjectUIEvent.Navigate(
                                                subject.subject
                                            )
                                        )
                                    }
                                )

                                Spacer(
                                    modifier = Modifier.height(12.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}