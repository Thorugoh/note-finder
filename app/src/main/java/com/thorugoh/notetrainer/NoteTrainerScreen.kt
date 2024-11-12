package com.thorugoh.notetrainer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.DisposableEffectScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thorugoh.notetrainer.ui.theme.NoteTrainerTheme
import kotlinx.serialization.Serializable

@Serializable
object NoteTrainer

@Composable
fun NoteTrainerScreen(viewModel: NoteTrainerViewModel = viewModel(), onQuit: () -> Unit) {
    val view = LocalView.current

    DisposableEffect(view) {
        view.keepScreenOn = true
        onDispose { view.keepScreenOn = false }
    }

    if(viewModel.noteToFindUiState is NoteToFindUiState.EndGame) {
        EndgameScreen(
            totalNotesFound = viewModel.score,
            onRestart = { viewModel.startGame() },
            onQuit = onQuit
        )
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Pontuação: ${viewModel.score}")
                Text(text = "Tempo: ${viewModel.timeLeft}")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Toque a Nota",
                fontSize = 24.sp,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Box (
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(color = Color.Blue, shape = CircleShape),
            ) {
                Text(
                    text = viewModel.currentNote,
                    fontSize = 48.sp,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteTrainerScreenPreview() {
    NoteTrainerTheme {
        NoteTrainerScreen(onQuit = {})
    }
}
