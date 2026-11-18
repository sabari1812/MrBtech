package com.example.mrbtech.presentation.important_notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mrbtech.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportantNotesScreen(
    department: String,
    onNavigate: (String) -> Unit,
    onPopBackStack: () -> Unit,
    viewModel: ImportantNotesViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Important Notes",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onPopBackStack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1172EC)
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
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
                ) {

                    Text(
                        text = "Selected Department",
                        color = Color(0xFF3C71B6)
                    )

                    Text(
                        text = department,
                        color = Color(0xFF14315C),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Choose your Semester",
                        color = Color(0xFF3C71B6)
                    )
                }
            }

            when (val state = uiState) {

                is ImportantNotesUIState.Loading -> {
                    CircularProgressIndicator()
                }

                is ImportantNotesUIState.Success -> {

                    LazyVerticalGrid(
                        columns = Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalArrangement =
                            Arrangement.spacedBy(12.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {

                        items(state.semesters.size) { index ->

                            val semester =
                                state.semesters[index]

                            val semesterImage = when (semester) {
                                1 -> R.drawable.semester_1
                                2 -> R.drawable.semester_2
                                3 -> R.drawable.semester_3
                                4 -> R.drawable.semester_4
                                5 -> R.drawable.semester_5
                                6 -> R.drawable.semester_6
                                7 -> R.drawable.semester_7
                                else -> R.drawable.semester_8
                            }

                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {

                                Image(
                                    painter = painterResource(
                                        semesterImage
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(105.dp)
                                        .clip(
                                            RoundedCornerShape(20.dp)
                                        )
                                        .clickable {
                                            onNavigate(
                                                "semester$semester"
                                            )
                                        }
                                )

                                Text(
                                    text = "Semester $semester",
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}