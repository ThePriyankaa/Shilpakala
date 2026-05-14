package com.shilpakala.navigation

sealed class Screen(val route: String) {
    object Splash    : Screen("splash")
    object Auth      : Screen("auth")
    object Login     : Screen("login")
    object Register  : Screen("register")
    object Gallery   : Screen("gallery")
    object Detail    : Screen("detail/{artworkId}") {
        fun createRoute(artworkId: String) = "detail/$artworkId"
    }
    object Heritage    : Screen("heritage")
    object Saved       : Screen("saved")
    object Profile     : Screen("profile")
    object ArtistDashboard : Screen("artist_dashboard")
    object UploadArtwork   : Screen("upload_artwork")
    object EditArtwork     : Screen("edit_artwork/{artworkId}") {
        fun createRoute(artworkId: String) = "edit_artwork/$artworkId"
    }
    object Notifications   : Screen("notifications")
    object ManageTimeline  : Screen("manage_timeline/{artworkId}") {
        fun createRoute(artworkId: String) = "manage_timeline/$artworkId"
    }
    object FullScreenImage : Screen("fullscreen/{artworkId}") {
        fun createRoute(artworkId: String) = "fullscreen/$artworkId"
    }
}

// Bottom-nav items for Buyer
sealed class BottomNavItem(
    val route: String,
    val label: String,
    val iconName: String          // material icon name string — resolved in NavBar composable
) {
    object Gallery  : BottomNavItem("gallery",  "Gallery",  "GridView")
    object Heritage : BottomNavItem("heritage", "Heritage", "AutoStories")
}
