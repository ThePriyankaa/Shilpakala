package com.shilpakala

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shilpakala.data.model.UserRole
import com.shilpakala.navigation.Screen
import com.shilpakala.navigation.ShilpaKalaNavGraph
import com.shilpakala.ui.theme.*
import com.shilpakala.viewmodel.ArtworkViewModel
import com.shilpakala.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShilpaKalaTheme {
                ShilpaKalaApp()
            }
        }
    }
}

@Composable
fun ShilpaKalaApp() {
    val navController = rememberNavController()
    val artworkViewModel: ArtworkViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val userRole by authViewModel.currentUserRole.collectAsStateWithLifecycle()
    val isGuest by authViewModel.isGuest.collectAsStateWithLifecycle()

    // Bottom nav visible only on main screens
    val showBottomNav = currentRoute in listOf(
        Screen.Gallery.route,
        Screen.Heritage.route,
        Screen.Saved.route
    )

    // Build bottom nav items dynamically based on role
    val bottomNavItems = remember(userRole, isGuest) {
        buildList {
            add(BottomNavItemData("Gallery", Icons.Default.GridView, Screen.Gallery.route))
            if (!isGuest && userRole == UserRole.BUYER) {
                add(BottomNavItemData("Saved", Icons.Default.Bookmark, Screen.Saved.route))
            }
            add(BottomNavItemData("Heritage", Icons.Default.AutoStories, Screen.Heritage.route))
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundBeige,
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomNav,
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut()
            ) {
                ShilpaKalaBottomBar(
                    navController = navController,
                    currentRoute = currentRoute,
                    items = bottomNavItems
                )
            }
        }
    ) { innerPadding ->
        ShilpaKalaNavGraph(
            navController = navController,
            viewModel = artworkViewModel,
            authViewModel = authViewModel
        )
    }
}

// ── Bottom Navigation ─────────────────────────────────────────────────────────

private data class BottomNavItemData(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
private fun ShilpaKalaBottomBar(
    navController: NavHostController,
    currentRoute: String?,
    items: List<BottomNavItemData>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(16.dp)
            .background(IvoryWhite)
    ) {
        // Gold top border
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Transparent,
                            LuxuryGold.copy(alpha = 0.6f),
                            LuxuryGold,
                            LuxuryGold.copy(alpha = 0.6f),
                            Color.Transparent
                        )
                    )
                )
        )

        NavigationBar(
            containerColor = Color.Transparent,
            contentColor = MediumBrown,
            tonalElevation = 0.dp,
            modifier = Modifier.navigationBarsPadding()
        ) {
            items.forEach { item ->
                val selected = currentRoute == item.route

                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(Screen.Gallery.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (selected) LuxuryGold else TextCaption
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 10.sp
                            ),
                            color = if (selected) LuxuryGold else TextCaption
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = LuxuryGold,
                        unselectedIconColor = TextCaption,
                        indicatorColor = ContainerGold
                    )
                )
            }
        }
    }
}
