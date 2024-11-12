package com.thorugoh.notetrainer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

import com.thorugoh.notetrainer.ui.theme.NoteTrainerTheme
import com.thorugoh.notetrainer.navigation.NavigationStack


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var onPermissionGranted: (() -> Unit)? = null

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (!isGranted) {
                Toast.makeText(this, "Microphone permission is required", Toast.LENGTH_SHORT).show()
            } else {
                onPermissionGranted?.invoke()
            }
        }

        val verifyPermission: (onGranted: () -> Unit) -> Unit ={ onGranted ->
            onPermissionGranted = onGranted

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }

        // Set up UI
        setContent {
            NoteTrainerTheme {
                NavigationStack(verifyPermission = verifyPermission)

            }
        }
    }

}


