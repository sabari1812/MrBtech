package com.example.mrbtech.presentation.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mrbtech.R
import com.example.mrbtech.util.HomeScreenBackgroundCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    dept:String,
    onNavigate:(HomeScreenUIEvent.NavigationHomeScreen)->Unit,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onDownloadClick:()-> Unit,
    onProgrammingClick:()-> Unit,
    onImportantNotesClick:()->Unit,
    onQuestionPaperClick:()-> Unit
) {

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event->
            when(event){
                is HomeScreenUIEvent.NavigationHomeScreen -> {
                    onNavigate(event)
                }
            }
        }
    }

    Scaffold { innerPadding->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.background),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(8.dp)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        Text(
                            text = "Home",
                            fontSize = 32.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )
                        IconButton(
                            onClick = {
                               onDownloadClick()
                            }
                        ){
                            Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                        Text(
                            text = "• Learn • Practice • Grow",
                            color = Color(0xFFBBD8FF)
                        )

                    Spacer(
                        modifier = Modifier
                            .height(135.dp)
                    )
                    Text(
                        text = "B.Tech ${onMapSubject(dept)}",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF174E91),
                        fontSize = 24.sp
                    )
                    Text(
                        text = "Department Notes",
                        color = Color(0xFF83BAF6),
                        fontSize = 16.sp
                    )
                    HomeScreenBackgroundCard(
                        background = R.drawable.background_theme1,
                        icon = R.drawable.notes_icon,
                        title = "Notes",
                        description = "All Subject Notes",
                        onClick = {
                            viewModel.onEvent(HomeScreenUIEvent.NavigationHomeScreen("notes"))
                        }
                    )
                    HomeScreenBackgroundCard(
                        background = R.drawable.background_theme2,
                        icon = R.drawable.idea_bulb_icon,
                        title = "Important Topics",
                        description = "All Subject Important Topics and Questions",
                        onClick = {
                            onImportantNotesClick()
                        }
                    )
                    HomeScreenBackgroundCard(
                        background = R.drawable.background_theme3,
                        icon = R.drawable.question_paper_icon,
                        title = "Question Papers",
                        description = "All Subject Question Papers",
                        onClick = {
                            onQuestionPaperClick()
                        }
                    )
                    HomeScreenBackgroundCard(
                        background = R.drawable.background_theme4,
                        icon = R.drawable.code_icon,
                        title = "Programming",
                        description = "Code,Example and Practice",
                        onClick = {
                            onProgrammingClick()
                        }
                    )
                }
            }
        }
    }

}

fun onMapSubject(dept:String): String{
    if(dept == "CSE") return "Computer Science"
    else if(dept == "IT") return "Information Technology"
    else if(dept == "ECE") return "Electronics Communication Engineering"
    else return "Mechanical Engineering"
}