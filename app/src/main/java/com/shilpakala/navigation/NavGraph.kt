package com.shilpakala.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shilpakala.ui.screens.*
import com.shilpakala.viewmodel.ArtworkViewModel
import com.shilpakala.viewmodel.AuthViewModel

@Composable
fun ShilpaKalaNavGraph(
    navController: NavHostController,
    viewModel: ArtworkViewModel,
    authViewModel: AuthViewModel
) {
    val userId by authViewModel.currentUserId.collectAsStateWithLifecycle()

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
            SplashScreen(navController, authViewModel)
        }

        composable(Screen.Auth.route) {
            AuthScreen(navController, authViewModel)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController, authViewModel)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController, authViewModel)
        }

        composable(Screen.Gallery.route) {
            GalleryScreen(navController, viewModel, authViewModel)
        }

        composable(Screen.Detail.route) { backStackEntry ->
            val artworkId = backStackEntry.arguments?.getString("artworkId") ?: return@composable
            ArtworkDetailScreen(artworkId, navController, viewModel, authViewModel)
        }

        composable(Screen.Heritage.route) {
            HeritageScreen(viewModel)
        }

        composable(Screen.FullScreenImage.route) { backStackEntry ->
            val artworkId = backStackEntry.arguments?.getString("artworkId") ?: return@composable
            FullScreenImageScreen(artworkId, navController, viewModel)
        }

        composable(Screen.Saved.route) {
            SavedArtworksScreen(navController, viewModel, userId)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController, authViewModel)
        }

        composable(Screen.ArtistDashboard.route) {
            ArtistDashboardScreen(navController, viewModel, authViewModel)
        }

        composable(Screen.UploadArtwork.route) {
            UploadArtworkScreen(navController, viewModel, authViewModel)
        }

        composable(Screen.EditArtwork.route) { backStackEntry ->
            val artworkId = backStackEntry.arguments?.getString("artworkId") ?: return@composable
            EditArtworkScreen(artworkId, navController, viewModel)
        }

        composable(Screen.ManageTimeline.route) { backStackEntry ->
            val artworkId = backStackEntry.arguments?.getString("artworkId") ?: return@composable
            ManageTimelineScreen(artworkId, navController, viewModel)
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(navController, viewModel, userId)
        }
    }
}
