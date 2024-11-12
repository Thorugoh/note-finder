package com.thorugoh.notetrainer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EndgameScreen(totalNotesFound: Int, onRestart: () -> Unit, onQuit: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Fim de Jogo!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Notas Encontradas: $totalNotesFound",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onRestart() },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        ) {
            Text(text = "Reiniciar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onQuit() },  // Exits the app
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        ) {
            Text(text = "Sair")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EndgameScreenPreview() {
    EndgameScreen(
        totalNotesFound = 25,
        onRestart = {}, // No-op for preview
        onQuit = {} // No-op for preview
    )
}