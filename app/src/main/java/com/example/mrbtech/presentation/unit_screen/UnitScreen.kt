package com.example.mrbtech.presentation.unit_screen

import android.util.Log
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
import com.example.mrbtech.domain.model.Unit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitScreen(
    department: String,
    semester: String,
    subjectName: String,
    onPopBackStack:()-> kotlin.Unit,
    viewModel: UnitScreenViewModel = hiltViewModel()
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
        is UnitScreenUIState.Loading -> {
            CircularProgressIndicator(
                color = Color(0xFF1172EC)
            )
        }

        is UnitScreenUIState.Success -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Unit",
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
                                text = "Subject",
                                color = Color(0xFF3C71B6)
                            )
                            Text(
                                text = subjectName,
                                color = Color(0xFF14315C),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Units",
                                color = Color(0xFF3C71B6)
                            )
                        }
                    }
                    Text(
                        text = "Units",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF124D97)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyColumn {
                        items(state.units) { unit ->
                            UnitCard(
                                unit = unit,
                                onClick = {
                                    viewModel.onEvent(UnitScreenUIEvent.DownloadFile(unit.pdfUrl,subjectName,unit.unitNumber))
                                    Log.d("MainActivityLog",unit.pdfUrl)
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

        }

        is UnitScreenUIState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = state.message)
            }
        }
        else -> null
    }
}
@Composable
fun UnitCard(
    unit: Unit,
    onClick: () -> kotlin.Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFCEDFEE))
            .height(60.dp)
            .clickable{onClick()}
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Unit ${unit.unitNumber}"
            )
            Image(
                imageVector = Icons.Default.ArrowRight,
                contentDescription = null
            )
        }
    }
}