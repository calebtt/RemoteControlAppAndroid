package com.example.remotecontrolappandroid


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.remotecontrolnativeapp.screens.ControlScreen
import com.example.remotecontrolnativeapp.screens.SettingsScreen
import com.example.remotecontrolnativeapp.state.ControlViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val sharedViewModel: ControlViewModel = viewModel() // correct: inside setContent

            MaterialTheme {
                Scaffold { paddingValues ->
                    NavHost(navController, startDestination = "control") {
                        composable("control") {
                            ControlScreen(navController, sharedViewModel)
                        }
                        composable("settings") {
                            SettingsScreen(navController, sharedViewModel)
                        }
                    }
                }
            }
        }


    }
}
