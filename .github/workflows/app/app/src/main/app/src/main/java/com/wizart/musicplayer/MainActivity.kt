package com.wizart.musicplayer

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

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

    val context = androidx.compose.ui.platform.LocalContext.current

    val player = remember {
        ExoPlayer.Builder(context).build()
    }

    var songName by remember {
        mutableStateOf("Нет выбранного трека")
    }

    var playing by remember {
        mutableStateOf(false)
    }

    val musicPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->

        if (uri != null) {

            val mediaItem = MediaItem.fromUri(uri)

            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()

            songName = uri.lastPathSegment ?: "Выбранный трек"
            playing = true
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

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

                Spacer(
                    modifier = Modifier.height(40.dp)
                )

                Text(
                    text = songName,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(30.dp)
                )

                Button(
                    onClick = {
                        musicPicker.launch("audio/*")
                    }
                ) {
                    Text("Выбрать музыку")
                }

                Spacer(
                    modifier = Modifier.height(30.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Button(
                        onClick = {
                            player.seekToPreviousMediaItem()
                        }
                    ) {
                        Text("⏮")
                    }

                    Button(
                        onClick = {

                            if (player.isPlaying) {
                                player.pause()
                                playing = false
                            } else {
                                player.play()
                                playing = true
                            }

                        }
                    ) {
                        Text(
                            if (playing) "⏸"
                            else "▶"
                        )
                    }

                    Button(
                        onClick = {
                            player.seekToNextMediaItem()
                        }
                    ) {
                        Text("⏭")
                    }
                }
            }
        }
    }
}
