@file:OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)

package com.example.remotecontrolnativeapp.ui

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GlowyPressButton(
    label: String,
    onPress: () -> Unit,
    onRelease: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 0.92f else 1f, label = "buttonScale")

    val interactionSource = remember { MutableInteractionSource() }

    Button(
        onClick = {},
        interactionSource = interactionSource,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF292F36)),
        modifier = Modifier
            .size(70.dp)
            .shadow(elevation = 6.dp, shape = CircleShape)
            .scale(scale)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        pressed = true
                        onPress()
                        true
                    }
                    MotionEvent.ACTION_UP,
                    MotionEvent.ACTION_CANCEL -> {
                        pressed = false
                        onRelease()
                        true
                    }
                    else -> false
                }
            },
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(label, fontSize = 22.sp, color = Color.White)
    }
}
