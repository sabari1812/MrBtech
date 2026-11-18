package com.example.mrbtech.presentation.important_notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowRight
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mrbtech.R
import com.example.mrbtech.domain.model.ImportantUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportantUnitScreen(
    department: String,
    semester: String,
    subjectName: String,
    onPopBackStack: () -> Unit,
    viewModel: ImportantUnitScreenViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(department, semester, subjectName) {
        viewModel.loadUnits(
            department = department,
            semester = semester,
            subjectName = subjectName
        )
    }

    when (val state = uiState) {

        is ImportantUnitUIState.Loading -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFF1172EC)
                )
            }
        }

        is ImportantUnitUIState.Error -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.message)
            }
        }

        is ImportantUnitUIState.Success -> {

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Important Units",
                                color = Color.White,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFF1172EC),
                            navigationIconContentColor = Color.White
                        ),
                        navigationIcon = {
                            IconButton(onClick = onPopBackStack) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back"
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
                                .padding(horizontal = 16.dp),
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
                                text = semester.replaceFirstChar {
                                    it.uppercase()
                                },
                                color = Color(0xFF3C71B6)
                            )

                            Text(
                                text = subjectName,
                                color = Color(0xFF14315C),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Important Units",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF124D97)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (state.units.isEmpty()) {

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No important units available")
                        }

                    } else {

                        LazyColumn {
                            items(state.units) { unit ->

                                ImportantUnitCard(
                                    unit = unit,
                                    onClick = {
                                        viewModel.onEvent(
                                            ImportantUnitUIEvent.DownloadFile(
                                                url = unit.pdfUrl,
                                                name = subjectName,
                                                unit = unit.unitNumber
                                            )
                                        )
                                    }
                                )

                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ImportantUnitCard(
    unit: ImportantUnit,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFCEDFEE))
            .height(60.dp)
            .clickable { onClick() }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = "Unit ${unit.unitNumber}")

            Icon(
                imageVector = Icons.Default.ArrowRight,
                contentDescription = null
            )
        }
    }
}