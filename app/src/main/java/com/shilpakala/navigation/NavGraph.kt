package com.shilpakala.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shilpakala.ui.screens.*
import com.shilpakala.viewmodel.ArtworkViewModel

@Composable
fun ShilpaKalaNavGraph(
    navController: NavHostController,
    viewModel: ArtworkViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        enterTransition = {
            fadeIn(tween(300)) + slideInHorizontally(tween(300)) { it / 4 }
        },
        exitTransition = {
            fadeOut(tween(200)) + slideOutHorizontally(tween(200)) { -it / 4 }
        },
        popEnterTransition = {
            fadeIn(tween(300)) + slideInHorizontally(tween(300)) { -it / 4 }
        },
        popExitTransition = {
            fadeOut(tween(200)) + slideOutHorizontally(tween(200)) { it / 4 }
        }
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Gallery.route) {
            GalleryScreen(navController, viewModel)
        }

        composable(Screen.Detail.route) { backStackEntry ->
            val artworkId = backStackEntry.arguments?.getString("artworkId") ?: return@composable
            ArtworkDetailScreen(artworkId, navController, viewModel)
        }

        composable(Screen.Timeline.route) { backStackEntry ->
            val artworkId = backStackEntry.arguments?.getString("artworkId") ?: "global"
            TimelineScreen(artworkId, navController, viewModel)
        }

        composable("timeline/global") {
            TimelineScreen("global", navController, viewModel)
        }

        composable(Screen.Heritage.route) {
            HeritageScreen(viewModel)
        }
    }
}
