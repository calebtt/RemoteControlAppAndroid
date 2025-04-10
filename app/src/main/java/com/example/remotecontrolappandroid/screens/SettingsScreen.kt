package com.example.remotecontrolnativeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.remotecontrolnativeapp.state.ControlViewModel

@Composable
fun SettingsScreen(navController: NavController, viewModel: ControlViewModel = viewModel()) {
    var inputToken by remember { mutableStateOf(viewModel.sessionToken) }
    val isConnected by viewModel.isConnected.collectAsState()
    val logs by viewModel.logs.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Settings", fontSize = 22.sp, color = Color.White)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = inputToken,
            onValueChange = { inputToken = it },
            label = { Text("Session Token") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            viewModel.sessionToken = inputToken
            viewModel.connect()
        }) {
            Text("Connect WebSocket")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isConnected) "✅ Connected" else "❌ Not Connected",
            color = if (isConnected) Color.Green else Color.Red,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Log Output", color = Color.White, fontSize = 16.sp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFF1E1E1E))
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column {
                logs.forEach { entry ->
                    Text(entry, color = Color.LightGray, fontSize = 13.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { navController.navigate("control") }) {
            Text("Back to Control Screen")
        }
    }
}
