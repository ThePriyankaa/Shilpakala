package com.shilpakala.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.shilpakala.ui.theme.*

/**
 * Premium artwork card with gold border, elegant typography, and press animation.
 * Used in the 2-column gallery grid.
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArtworkCard(
    imageUrl: String,
    title: String,
    category: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "card_scale"
    )

    val elevation by animateFloatAsState(
        targetValue = if (isPressed) 2f else 8f,
        label = "card_elevation"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .shadow(elevation.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(CardSurface)
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    listOf(LuxuryGold.copy(alpha = 0.6f), LuxuryGold.copy(alpha = 0.2f))
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        Column {
            // ── Image ─────────────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f)
            ) {
                GlideImage(
                    model = imageUrl,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    transition = CrossFade,
                    loading = placeholder { ShimmerBox(Modifier.fillMaxSize()) },
                    failure = placeholder {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(ContainerBeige),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🏛", fontSize = 32.sp)
                        }
                    }
                )
                // Gradient overlay at bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, DarkBrown.copy(alpha = 0.45f))
                            )
                        )
                )
            }

            // ── Text info ─────────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                // Category pill
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(ContainerGold, RoundedCornerShape(20.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.labelSmall,
                        color = LuxuryGoldDark,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(Modifier.height(5.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Skeleton shimmer placeholder for loading state.
 */
@Composable
fun ShimmerBox(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer_alpha"
    )
    Box(
        modifier = modifier.background(
            Brush.linearGradient(
                listOf(
                    DividerColor.copy(alpha = alpha),
                    ContainerBeige.copy(alpha = alpha * 0.6f),
                    DividerColor.copy(alpha = alpha)
                )
            )
        )
    )
}

/**
 * Skeleton card used in the gallery grid during loading.
 */
@Composable
fun SkeletonArtworkCard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(CardSurface)
    ) {
        Column {
            ShimmerBox(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f)
            )
            Column(Modifier.padding(12.dp)) {
                ShimmerBox(Modifier.fillMaxWidth(0.4f).height(14.dp).clip(RoundedCornerShape(6.dp)))
                Spacer(Modifier.height(8.dp))
                ShimmerBox(Modifier.fillMaxWidth(0.8f).height(16.dp).clip(RoundedCornerShape(6.dp)))
                Spacer(Modifier.height(4.dp))
                ShimmerBox(Modifier.fillMaxWidth(0.5f).height(14.dp).clip(RoundedCornerShape(6.dp)))
            }
        }
    }
}

/**
 * Gold "Enquire on WhatsApp" button with bounce animation.
 */
@Composable
fun WhatsAppEnquireButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "wa_btn_scale"
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .scale(scale)
            .fillMaxWidth()
            .height(56.dp),
        interactionSource = interactionSource,
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = LuxuryGold,
            contentColor = DarkBrown
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 2.dp
        )
    ) {
        Text("💬  Enquire on WhatsApp", fontWeight = FontWeight.Bold, fontSize = 15.sp)
    }
}

/**
 * Thin gold divider with optional label.
 */
@Composable
fun GoldDivider(modifier: Modifier = Modifier, label: String? = null) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .weight(1f)
                .height(1.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(Color.Transparent, LuxuryGold.copy(alpha = 0.5f))
                    )
                )
        )
        if (label != null) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = LuxuryGold,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
        Box(
            Modifier
                .weight(1f)
                .height(1.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(LuxuryGold.copy(alpha = 0.5f), Color.Transparent)
                    )
                )
        )
    }
}

/**
 * Empty gallery state with elegant illustration.
 */
@Composable
fun EmptyGalleryState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🏛", fontSize = 64.sp)
        Spacer(Modifier.height(16.dp))
        Text(
            "No artworks found",
            style = MaterialTheme.typography.headlineSmall,
            color = TextPrimary
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Try adjusting your search or category filter",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )
    }
}
