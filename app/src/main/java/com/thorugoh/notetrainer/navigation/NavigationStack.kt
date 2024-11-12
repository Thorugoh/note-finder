package com.thorugoh.notetrainer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thorugoh.notetrainer.MainMenu
import com.thorugoh.notetrainer.NoteTrainer
import com.thorugoh.notetrainer.NoteTrainerScreen

@Composable
fun NavigationStack(verifyPermission: (onGranted: () -> Unit) -> Unit){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_menu"){
        composable(route = "main_menu"){
            MainMenu(onStartNewGame = {
                verifyPermission { navController.navigate(route = NoteTrainer) }
            }, {
                navController.navigate(route = "main_menu")
            })
        }

        composable<NoteTrainer> {
            NoteTrainerScreen(onQuit = {
                navController.navigate(route = "main_menu")
            })
        }

    }

}