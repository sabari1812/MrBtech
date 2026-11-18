package com.example.mrbtech.presentation.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mrbtech.presentation.downloaded_pdf.DownloadsScreen
import com.example.mrbtech.presentation.home_screen.HomeScreen
import com.example.mrbtech.presentation.important_notes.ImportantSubjectScreen
import com.example.mrbtech.presentation.important_notes.ImportantUnitScreen
import com.example.mrbtech.presentation.important_notes.ImportantNotesScreen
import com.example.mrbtech.presentation.login_screen.LoginScreen
import com.example.mrbtech.presentation.notes_screen.NotesScreen
import com.example.mrbtech.presentation.programing_screen.ProgrammingScreen
import com.example.mrbtech.presentation.programing_screen.ProgrammingWebScreen
import com.example.mrbtech.presentation.question_paper.QuestionPaperScreen
import com.example.mrbtech.presentation.question_paper.QuestionPaperSubjectScreen
import com.example.mrbtech.presentation.subject_screen.SubjectScreen
import com.example.mrbtech.presentation.unit_screen.UnitScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login"){
            LoginScreen(onNavigate = {department->
                navController.navigate("home/${department.route}")
            })
        }
        composable("home/{department}"){entry->
            val department = entry.arguments?.getString("department")?:"CSE"
            HomeScreen(department, onNavigate = {
                navController.navigate("notes/${department}")
            },
                onDownloadClick = {
                    navController.navigate("downloaded")
                },
                onProgrammingClick = {
                    navController.navigate("programming")
                },
                onImportantNotesClick = {
                    navController.navigate("important-notes/$department")
                },
                onQuestionPaperClick = {
                    navController.navigate("question-papers/$department")
                }
            )
        }
        composable("notes/{department}"){entry->
            val department = entry.arguments?.getString("department")?:"CSE"
            NotesScreen(
                onNavigate = {semester->
                    navController.navigate("subject/${semester.route}/notes/$department")
                },
                onPopBackStack = {
                    navController.popBackStack()
                }
            )
        }
        composable("subject/{semester}/notes/{department}"){ entry->
            val semester = entry.arguments?.getString("semester")?:"semester1"
            val department = entry.arguments?.getString("department")?:"CSE"
            Log.d("MainActivity",department+semester)
            SubjectScreen(
                department,
                semester,
                onPopBackStack = {
                    navController.popBackStack()
                },
                onNavigate = {subject->
                    navController.navigate("unit/${subject.route}/$semester/$department")
                })
        }
        composable("unit/{subject}/{semester}/{department}"){entry->
            val department = entry.arguments?.getString("department")?:"CSE"
            val semester = entry.arguments?.getString("semester")?:"semester1"
            val subject = entry.arguments?.getString("subject")?:"mathematics"
            Log.d("MainActivityARG",subject)
            UnitScreen(
                department = department,
                semester = semester,
                subjectName = subject,
                onPopBackStack = {
                    navController.popBackStack()
                }
            )
        }
        composable("downloaded") {
            DownloadsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable("programming") {

            ProgrammingScreen(

                onLanguageClick = { language ->

                    val url = when (language) {
                        "Java" -> "https://www.w3schools.com/java/"
                        "Python" -> "https://www.w3schools.com/python/"
                        "C" -> "https://www.w3schools.com/c/"
                        "C++" -> "https://www.w3schools.com/cpp/"
                        "Kotlin" -> "https://kotlinlang.org/docs/home.html"
                        else -> "https://www.w3schools.com/"
                    }

                    navController.navigate(
                        "webview/${Uri.encode(url)}"
                    )
                },

                onExploreClick = { section ->

                    val url = when (section) {

                        "DSA" ->
                            "https://www.geeksforgeeks.org/dsa/"

                        "Examples" ->
                            "https://www.programiz.com/"

                        "Quiz" ->
                            "https://www.hackerrank.com/domains"

                        "Interview" ->
                            "https://www.geeksforgeeks.org/interview-preparation/"

                        else ->
                            "https://www.geeksforgeeks.org/"
                    }

                    navController.navigate(
                        "webview/${Uri.encode(url)}"
                    )
                }
            )
        }
        composable("webview/{url}") { entry ->

            val url = Uri.decode(
                entry.arguments?.getString("url") ?: ""
            )

            ProgrammingWebScreen(url = url)
        }
        composable("important-notes/{department}") { entry ->

            val department =
                entry.arguments?.getString("department") ?: "CSE"

            ImportantNotesScreen(

                department = department,

                onNavigate = { semester ->

                    navController.navigate(
                        "important-subject/$semester/$department"
                    )
                },

                onPopBackStack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            "important-subject/{semester}/{department}"
        ) { entry ->

            val semester =
                entry.arguments?.getString("semester") ?: "semester1"

            val department =
                entry.arguments?.getString("department") ?: "CSE"

            ImportantSubjectScreen(

                department = department,

                semester = semester,

                onNavigate = { subject ->

                    navController.navigate(
                        "important-unit/${subject.route}/$semester/$department"
                    )
                },

                onPopBackStack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            "important-unit/{subject}/{semester}/{department}"
        ) { entry ->

            val subject =
                entry.arguments?.getString("subject") ?: "mathematics"

            val semester =
                entry.arguments?.getString("semester") ?: "semester1"

            val department =
                entry.arguments?.getString("department") ?: "CSE"

            ImportantUnitScreen(
                department = department,
                semester = semester,
                subjectName = subject,
                onPopBackStack = {
                    navController.popBackStack()
                }
            )
        }
        composable("question-papers/{department}") { entry ->

            val department =
                entry.arguments?.getString("department") ?: "CSE"

            QuestionPaperScreen(
                department = department,
                onNavigate = { semester ->
                    navController.navigate(
                        "question-paper-subjects/$semester/$department"
                    )
                },
                onPopBackStack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            "question-paper-subjects/{semester}/{department}"
        ) { entry ->

            val semester =
                entry.arguments?.getString("semester") ?: "semester1"

            val department =
                entry.arguments?.getString("department") ?: "CSE"

            QuestionPaperSubjectScreen(
                department = department,
                semester = semester,
                onPopBackStack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
