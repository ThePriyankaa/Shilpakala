package com.shilpakala.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.shilpakala.navigation.Screen
import com.shilpakala.ui.components.*
import com.shilpakala.ui.theme.*
import com.shilpakala.viewmodel.ArtworkViewModel
import com.shilpakala.viewmodel.AuthViewModel
import com.shilpakala.data.model.TimelineStage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtworkDetailScreen(
    artworkId: String,
    navController: NavHostController,
    viewModel: ArtworkViewModel,
    authViewModel: AuthViewModel
) {
    LaunchedEffect(artworkId) { viewModel.selectArtworkById(artworkId) }
    val artwork by viewModel.selectedArtwork.collectAsStateWithLifecycle()
    val userId by authViewModel.currentUserId.collectAsStateWithLifecycle()
    val isGuest by authViewModel.isGuest.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val timelineStages by viewModel.timelineStages.collectAsStateWithLifecycle()

    // Record view
    LaunchedEffect(artworkId, userId) {
        if (userId > 0) viewModel.recordView(userId, artworkId)
    }

    artwork?.let { art ->
        // Engagement state
        val isLiked by viewModel.isLiked(userId, art.id).collectAsStateWithLifecycle(false)
        val isSaved by viewModel.isSaved(userId, art.id).collectAsStateWithLifecycle(false)

        Box(Modifier.fillMaxSize().background(BackgroundBeige)) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
            ) {
                // ── Hero Image with zoom ─────────────────────────────────────
                ZoomableImageSection(
                    imageUrl = art.imageUrl, title = art.title,
                    onFullScreen = { navController.navigate(Screen.FullScreenImage.createRoute(art.id)) },
                    modifier = Modifier.fillMaxWidth().height(420.dp)
                )

                // ── Content ──────────────────────────────────────────────────
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(BackgroundBeige, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .offset(y = (-16).dp)
                        .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 100.dp)
                ) {
                    // Product ID badge + category
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.background(ContainerGold, RoundedCornerShape(8.dp)).padding(horizontal = 10.dp, vertical = 4.dp)) {
                            Text(art.productId, style = MaterialTheme.typography.labelMedium, color = LuxuryGoldDark, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.weight(1f))
                        Text(art.category, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                    }

                    Spacer(Modifier.height(12.dp))
                    Text(art.title, style = MaterialTheme.typography.displayMedium, color = DarkBrown)
                    Spacer(Modifier.height(6.dp))
                    Text("by ${art.artistName}", style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic), color = LuxuryGold)
                    Spacer(Modifier.height(6.dp))
                    Text(art.price, style = MaterialTheme.typography.headlineMedium.copy(color = TerracottaAccent, fontWeight = FontWeight.Bold))

                    Spacer(Modifier.height(12.dp))

                    // ── Like & Save Buttons ──────────────────────────────────
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(
                            onClick = {
                                if (!isGuest && userId > 0) viewModel.toggleLike(userId, art.id)
                                else Toast.makeText(context, "Login to like artworks", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f).height(44.dp),
                            shape = RoundedCornerShape(22.dp),
                            border = BorderStroke(1.dp, if (isLiked) TerracottaAccent else DividerColor),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (isLiked) TerracottaAccent.copy(alpha = 0.08f) else Color.Transparent
                            )
                        ) {
                            Icon(
                                if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                null, tint = if (isLiked) TerracottaAccent else TextCaption, modifier = Modifier.size(18.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(if (isLiked) "Liked" else "Like", color = if (isLiked) TerracottaAccent else TextSecondary, fontSize = 13.sp)
                        }
                        OutlinedButton(
                            onClick = {
                                if (!isGuest && userId > 0) viewModel.toggleSave(userId, art.id)
                                else Toast.makeText(context, "Login to save artworks", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f).height(44.dp),
                            shape = RoundedCornerShape(22.dp),
                            border = BorderStroke(1.dp, if (isSaved) LuxuryGold else DividerColor),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (isSaved) ContainerGold else Color.Transparent
                            )
                        ) {
                            Icon(
                                if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                null, tint = if (isSaved) LuxuryGoldDark else TextCaption, modifier = Modifier.size(18.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(if (isSaved) "Saved" else "Save", color = if (isSaved) LuxuryGoldDark else TextSecondary, fontSize = 13.sp)
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    GoldDivider()
                    Spacer(Modifier.height(16.dp))

                    // ── Metadata grid ────────────────────────────────────────
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        MetaChip(Icons.Default.WorkspacePremium, "Material", art.material, Modifier.weight(1f))
                        MetaChip(Icons.Default.Straighten, "Dimensions", art.dimensions, Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        MetaChip(Icons.Default.Person, "Artist", art.artistName, Modifier.weight(1f))
                        MetaChip(Icons.Default.CalendarMonth, "Year", art.year, Modifier.weight(1f))
                    }

                    Spacer(Modifier.height(20.dp))

                    // ── Description ──────────────────────────────────────────
                    Text("About this Piece", style = MaterialTheme.typography.headlineSmall, color = DarkBrown)
                    Spacer(Modifier.height(10.dp))
                    Text(art.description, style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 26.sp), color = TextPrimary)

                    Spacer(Modifier.height(24.dp))

                    // ── Timeline preview ─────────────────────────────────────
                    if (timelineStages.isNotEmpty()) {
                        GoldDivider(label = "Creation Journey")
                        Spacer(Modifier.height(16.dp))
                        Text("Work in Progress", style = MaterialTheme.typography.headlineSmall, color = DarkBrown)
                        Spacer(Modifier.height(4.dp))
                        Text("See how this sculpture transforms from raw stone to masterpiece",
                            style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic), color = TextSecondary)
                        Spacer(Modifier.height(16.dp))

                        timelineStages.forEachIndexed { index, stage ->
                            TimelineStageCard(
                                stage = stage,
                                index = index,
                                isLast = index == timelineStages.lastIndex
                            )
                        }
                        
                        Spacer(Modifier.height(24.dp))
                    }

                    // ── WhatsApp enquiry ─────────────────────────────────────
                    GoldDivider(label = "Enquire")
                    Spacer(Modifier.height(16.dp))
                    Text("Interested in this piece?",
                        style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                        color = TextSecondary, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(8.dp))
                    WhatsAppEnquireButton(onClick = {
                        openWhatsAppEnquiry(context, art.productId, art.title, art.whatsappNumber)
                        if (!isGuest && userId > 0) viewModel.recordInquiry(userId, art)
                    })
                    Spacer(Modifier.height(8.dp))
                    Text("We typically respond within 2 hours",
                        style = MaterialTheme.typography.labelSmall, color = TextCaption, modifier = Modifier.fillMaxWidth())
                }
            }

            // ── Top navigation bar (floating) ─────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth().windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)).padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FloatingNavButton(onClick = { navController.popBackStack() }, icon = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                FloatingNavButton(onClick = { shareArtwork(context, art.title, art.description, art.productId, art.imageUrl) }, icon = Icons.Default.Share, contentDescription = "Share")
            }
        }
    } ?: run {
        Box(Modifier.fillMaxSize().background(BackgroundBeige), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = LuxuryGold)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ZoomableImageSection(
    imageUrl: String, title: String, onFullScreen: () -> Unit, modifier: Modifier = Modifier
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(bottomStart = 0.dp, bottomEnd = 0.dp))
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(1f, 4f)
                    if (scale > 1f) { offsetX += pan.x; offsetY += pan.y }
                    else { offsetX = 0f; offsetY = 0f }
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(onDoubleTap = {
                    if (scale > 1f) { scale = 1f; offsetX = 0f; offsetY = 0f } else { scale = 2.5f }
                })
            }
    ) {
        GlideImage(
            model = imageUrl, contentDescription = title, contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().graphicsLayer(scaleX = scale, scaleY = scale, translationX = offsetX, translationY = offsetY),
            transition = CrossFade,
            loading = placeholder { ShimmerBox(Modifier.fillMaxSize()) }
        )
        Box(Modifier.fillMaxWidth().height(100.dp).align(Alignment.TopCenter)
            .background(Brush.verticalGradient(listOf(DarkBrown.copy(alpha = 0.5f), Color.Transparent))))
        // Fullscreen button
        IconButton(
            onClick = onFullScreen,
            modifier = Modifier.align(Alignment.BottomStart).padding(12.dp).size(36.dp)
                .background(DarkBrown.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
        ) { Icon(Icons.Default.Fullscreen, "Full screen", tint = Color.White) }
        // Zoom hint
        Box(Modifier.align(Alignment.BottomEnd).padding(12.dp)
            .background(DarkBrown.copy(alpha = 0.6f), RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
            Text("Pinch to zoom", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.9f))
        }
    }
}

@Composable
private fun MetaChip(icon: ImageVector, label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier.background(CardSurface, RoundedCornerShape(12.dp)).border(1.dp, DividerColor, RoundedCornerShape(12.dp)).padding(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = LuxuryGold, modifier = Modifier.size(14.dp))
            Spacer(Modifier.width(4.dp))
            Text(label, style = MaterialTheme.typography.labelSmall, color = TextCaption)
        }
        Spacer(Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.labelMedium, color = TextPrimary, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun FloatingNavButton(onClick: () -> Unit, icon: ImageVector, contentDescription: String) {
    IconButton(onClick = onClick, modifier = Modifier.size(40.dp).background(IvoryWhite.copy(alpha = 0.88f), CircleShape)) {
        Icon(icon, contentDescription = contentDescription, tint = DarkBrown)
    }
}

/**
 * Opens WhatsApp with a prefilled enquiry message.
 * Uses the artwork's configurable WhatsApp number.
 */
private fun openWhatsAppEnquiry(context: Context, productId: String, title: String, whatsappNumber: String) {
    val message = "Hello, I am interested in $title (Product ID: $productId). Could you please share more details?"
    val phone = whatsappNumber.replace("+", "").replace(" ", "")
    try {
        // Try WhatsApp directly first
        val waIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://wa.me/$phone?text=${Uri.encode(message)}")
            setPackage("com.whatsapp")
        }
        context.startActivity(waIntent)
    } catch (e: Exception) {
        try {
            // Fallback to wa.me URL (opens browser or any WhatsApp variant)
            val fallback = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://wa.me/$phone?text=${Uri.encode(message)}")
            }
            context.startActivity(fallback)
        } catch (e2: Exception) {
            Toast.makeText(context, "WhatsApp is not installed on this device", Toast.LENGTH_SHORT).show()
        }
    }
}

/**
 * Shares artwork with title, description, product ID, and image URL.
 */
private fun shareArtwork(context: Context, title: String, description: String, productId: String, imageUrl: String) {
    val shareText = buildString {
        appendLine("✨ $title")
        appendLine("Product ID: $productId")
        appendLine()
        appendLine(description.take(200))
        if (description.length > 200) append("...")
        appendLine()
        appendLine("🔗 View on Shilpa-Kala Showcase")
        appendLine(imageUrl)
        appendLine()
        appendLine("— Shilpa-Kala: Preserving Heritage Through Digital Art")
    }
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Shilpa-Kala: $title ($productId)")
        putExtra(Intent.EXTRA_TEXT, shareText)
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share Artwork"))
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun TimelineStageCard(
    stage: TimelineStage,
    index: Int,
    isLast: Boolean
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(400, delayMillis = index * 150),
        label = "stage_alpha_$index"
    )
    val translateX by animateFloatAsState(
        targetValue = if (visible) 0f else 40f,
        animationSpec = tween(400, delayMillis = index * 150, easing = EaseOutQuart),
        label = "stage_tx_$index"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayerAlpha(alpha, translateX)
    ) {
        // ── Left: timeline spine ─────────────────────────────────────────────
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(48.dp)
        ) {
            // Stage number circle
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        Brush.radialGradient(listOf(LuxuryGold, LuxuryGoldDark)),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${stage.stageOrder}",
                    style = MaterialTheme.typography.labelLarge,
                    color = IvoryWhite,
                    fontWeight = FontWeight.Bold
                )
            }
            // Connecting line (except last)
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(260.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(LuxuryGold.copy(alpha = 0.8f), LuxuryGold.copy(alpha = 0.1f))
                            )
                        )
                )
            }
        }

        Spacer(Modifier.width(16.dp))

        // ── Right: stage content ──────────────────────────────────────────────
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (isLast) 0.dp else 24.dp)
        ) {
            // Stage title & duration
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stage.stageTitle,
                    style = MaterialTheme.typography.headlineSmall,
                    color = DarkBrown,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    Modifier
                        .background(ContainerGold, RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "${stage.durationDays}d",
                        style = MaterialTheme.typography.labelSmall,
                        color = LuxuryGoldDark,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // Stage image loaded via Glide
            GlideImage(
                model = stage.stageImage,
                contentDescription = stage.stageTitle,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(14.dp)),
                transition = CrossFade,
                loading = placeholder { ShimmerBox(Modifier.fillMaxSize()) }
            )

            Spacer(Modifier.height(10.dp))

            // Description
            Text(
                text = stage.stageDescription,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 24.sp),
                color = TextPrimary
            )
        }
    }
}

// Compose modifier extension for combined alpha + translationX
private fun Modifier.graphicsLayerAlpha(alpha: Float, translateX: Float): Modifier =
    this.then(
        Modifier.graphicsLayer(alpha = alpha, translationX = translateX)
    )

private val EaseOutQuart = Easing { fraction -> 1f - (1f - fraction).let { it * it * it * it } }
