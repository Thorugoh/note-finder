package com.thorugoh.notetrainer

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable


@Serializable
object MainMenu

@Composable
fun MainMenu(
    onStartNewGame: () -> Unit,
    onViewRankings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Nota Certa", fontSize = 32.sp)

        Spacer(modifier = Modifier.height(46.dp))

        Button(
            onClick = onStartNewGame,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Iniciar treino")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onViewRankings,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Ver rankings")
        }
    }
}

@Preview
@Composable
fun MainMenuPreview() {
    MainMenu({}, {})
}