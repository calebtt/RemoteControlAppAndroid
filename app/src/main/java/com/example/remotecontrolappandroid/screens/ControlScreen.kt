package com.example.remotecontrolnativeapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.remotecontrolnativeapp.state.ControlViewModel
import com.example.remotecontrolnativeapp.ui.GlowyPressButton

@Composable
fun ControlScreen(navController: NavController, viewModel: ControlViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        if (viewModel.sessionToken.isNotBlank()) {
            viewModel.connect()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF282C34)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Remote Control", color = Color.White, fontSize = 22.sp)
                IconButton(onClick = { navController.navigate("settings") }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                GlowyPressButton("⏮", { viewModel.sendCommand("previous_track", "keydown") }, { viewModel.sendCommand("previous_track", "keyup") })
                GlowyPressButton("▶️/⏸", { viewModel.sendCommand("play_pause", "keydown") }, { viewModel.sendCommand("play_pause", "keyup") })
                GlowyPressButton("⏭", { viewModel.sendCommand("next_track", "keydown") }, { viewModel.sendCommand("next_track", "keyup") })
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                GlowyPressButton("⏹", { viewModel.sendCommand("stop", "keydown") }, { viewModel.sendCommand("stop", "keyup") })
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                GlowyPressButton("🔉", { viewModel.sendCommand("volume_down", "keydown") }, { viewModel.sendCommand("volume_down", "keyup") })
                GlowyPressButton("🔇", { viewModel.sendCommand("mute_toggle", "keydown") }, { viewModel.sendCommand("mute_toggle", "keyup") })
                GlowyPressButton("🔊", { viewModel.sendCommand("volume_up", "keydown") }, { viewModel.sendCommand("volume_up", "keyup") })
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Directional Pad", color = Color.White, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    GlowyPressButton("↖", { viewModel.sendCommand("move_up_left", "keydown") }, { viewModel.sendCommand("move_up_left", "keyup") })
                    GlowyPressButton("↑", { viewModel.sendCommand("move_up", "keydown") }, { viewModel.sendCommand("move_up", "keyup") })
                    GlowyPressButton("↗", { viewModel.sendCommand("move_up_right", "keydown") }, { viewModel.sendCommand("move_up_right", "keyup") })
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    GlowyPressButton("←", { viewModel.sendCommand("move_left", "keydown") }, { viewModel.sendCommand("move_left", "keyup") })
                    GlowyPressButton("⭕", { viewModel.sendCommand("center_click", "keydown") }, { viewModel.sendCommand("center_click", "keyup") })
                    GlowyPressButton("→", { viewModel.sendCommand("move_right", "keydown") }, { viewModel.sendCommand("move_right", "keyup") })
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    GlowyPressButton("↙", { viewModel.sendCommand("move_down_left", "keydown") }, { viewModel.sendCommand("move_down_left", "keyup") })
                    GlowyPressButton("↓", { viewModel.sendCommand("move_down", "keydown") }, { viewModel.sendCommand("move_down", "keyup") })
                    GlowyPressButton("↘", { viewModel.sendCommand("move_down_right", "keydown") }, { viewModel.sendCommand("move_down_right", "keyup") })
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    GlowyPressButton("L", { viewModel.sendCommand("click_left", "keydown") }, { viewModel.sendCommand("click_left", "keyup") })
                    GlowyPressButton("R", { viewModel.sendCommand("click_right", "keydown") }, { viewModel.sendCommand("click_right", "keyup") })
                }
            }
        }
    }
}
