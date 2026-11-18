package com.example.mrbtech.presentation.subject_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mrbtech.R
import com.example.mrbtech.util.SubjectCard
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen(
    department:String,
    semester:String,
    onNavigate:(SubjectUIEvent.Navigate)-> Unit,
    onPopBackStack:()->Unit,
    viewModel: SubjectScreenViewModel = hiltViewModel(),

) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event->
            when(event){
                is SubjectUIEvent.Navigate -> {
                    onNavigate(event)
                }
            }
        }
    }
    LaunchedEffect(key1 = department, key2 = semester) {
        viewModel.loadSubject(
            department = department,
            semester = semester
        )
    }
    when(val state = uiState.value) {
        is SubjectScreenUIState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.value.toString()
                )
            }
        }

        SubjectScreenUIState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFF1172EC)
                )
            }
        }

        is SubjectScreenUIState.Success -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Subject",
                                color = Color.White,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }, colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFF1172EC),
                            navigationIconContentColor = Color.White,
                            actionIconContentColor = Color.White
                        ),
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    onPopBackStack()
                                }
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
                        .padding(paddingValues = paddingValues)
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){

                        Image(
                            painter = painterResource(R.drawable.semester_background),
                            contentDescription = null,
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
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
                                text = semester.replaceFirstChar { it.uppercase() },
                                color = Color(0xFF3C71B6)
                            )
                        }
                    }
                    Text(
                        text = "Subjects",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF124D97)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    state.subjects.forEach { subject->
                        Log.d("MainActivity",subject.subjectName)
                        SubjectCard(
                            subjectName = subject.subjectName,
                            onClick = {
                                viewModel.onEvent(
                                    SubjectUIEvent.Navigate(subject.subject)
                                )
                            }

                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                }
            }
        }
    }
}

