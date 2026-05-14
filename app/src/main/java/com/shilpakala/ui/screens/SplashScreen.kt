package com.shilpakala.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.shilpakala.navigation.Screen
import com.shilpakala.ui.theme.*
import com.shilpakala.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController, authViewModel: AuthViewModel) {

    val isLoggedIn by authViewModel.isLoggedIn.collectAsStateWithLifecycle()
    val isGuest by authViewModel.isGuest.collectAsStateWithLifecycle()

    // ── Animations ────────────────────────────────────────────────────────────
    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 1200, easing = EaseInOut),
        label = "splash_fade"
    )
    val logoScale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.85f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "logo_scale"
    )

    LaunchedEffect(Unit) {
        visible = true
        delay(2800)
        // Route based on session state
        val destination = when {
            isLoggedIn -> Screen.Gallery.route
            isGuest -> Screen.Gallery.route
            else -> Screen.Auth.route
        }
        navController.navigate(destination) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    // ── UI ────────────────────────────────────────────────────────────────────
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(IvoryWhite, BackgroundBeige, ContainerBeige)
                )
            )
            .statusBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .alpha(alpha)
                .padding(horizontal = 40.dp)
        ) {

            // ── Ornamental frame logo ─────────────────────────────────────────
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        Brush.radialGradient(
                            listOf(ContainerGold, LuxuryGold.copy(alpha = 0.3f))
                        ),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("🏛", fontSize = 52.sp)
            }

            Spacer(Modifier.height(8.dp))

            // ── App Title ────────────────────────────────────────────────────
            Text(
                text = "Shilpa-Kala",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontFamily = CursiveFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 38.sp,
                    letterSpacing = 2.sp
                ),
                color = DarkBrown,
                textAlign = TextAlign.Center
            )
            Text(
                text = "SHOWCASE",
                style = MaterialTheme.typography.labelLarge.copy(
                    letterSpacing = 8.sp,
                    fontSize = 13.sp
                ),
                color = LuxuryGold,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(4.dp))

            // Gold ornamental divider
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Box(Modifier.weight(1f).height(1.dp).background(LuxuryGold.copy(alpha = 0.5f)))
                Text("  ✦  ", color = LuxuryGold, fontSize = 14.sp)
                Box(Modifier.weight(1f).height(1.dp).background(LuxuryGold.copy(alpha = 0.5f)))
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text = "Preserving Heritage\nThrough Digital Art",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontStyle = FontStyle.Italic,
                    lineHeight = 24.sp
                ),
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
        }

        // Loading dots at bottom
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .alpha(alpha)
                .padding(bottom = 60.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(3) { index ->
                val dotAlpha by rememberInfiniteTransition(label = "dot_$index")
                    .animateFloat(
                        initialValue = 0.3f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            tween(600, delayMillis = index * 200),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "dot_alpha_$index"
                    )
                Box(
                    Modifier
                        .size(8.dp)
                        .alpha(dotAlpha)
                        .background(LuxuryGold, androidx.compose.foundation.shape.CircleShape)
                )
            }
        }
    }
}
