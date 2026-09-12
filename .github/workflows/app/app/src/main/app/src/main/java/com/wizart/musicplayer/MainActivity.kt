package com.wizart.musicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MusicPlayer()
        }
    }
}

@Composable
fun MusicPlayer() {
    var playing by remember { mutableStateOf(false) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "🎵 Music Player",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Нет выбранного трека",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(30.dp))

                Slider(
                    value = 0f,
                    onValueChange = {}
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = {}) {
                        Text("⏮")
                    }

                    Button(onClick = {
                        playing = !playing
                    }) {
                        Text(if (playing) "⏸" else "▶")
                    }

                    Button(onClick = {}) {
                        Text("⏭")
                    }
                }
            }
        }
    }
}
