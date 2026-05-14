package com.shilpakala.navigation

sealed class Screen(val route: String) {
    object Splash    : Screen("splash")
    object Gallery   : Screen("gallery")
    object Detail    : Screen("detail/{artworkId}") {
        fun createRoute(artworkId: String) = "detail/$artworkId"
    }
    object Timeline  : Screen("timeline/{artworkId}") {
        fun createRoute(artworkId: String) = "timeline/$artworkId"
    }
    object Heritage  : Screen("heritage")
}

// Bottom-nav items
sealed class BottomNavItem(
    val route: String,
    val label: String,
    val iconName: String          // material icon name string — resolved in NavBar composable
) {
    object Gallery  : BottomNavItem("gallery",  "Gallery",  "GridView")
    object Timeline : BottomNavItem("timeline/global", "Timeline", "Timeline")
    object Heritage : BottomNavItem("heritage", "Heritage", "AutoStories")
}
