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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Person
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtworkDetailScreen(
    artworkId: String,
    navController: NavHostController,
    viewModel: ArtworkViewModel
) {
    LaunchedEffect(artworkId) { viewModel.selectArtworkById(artworkId) }
    val artwork by viewModel.selectedArtwork.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    artwork?.let { art ->
        Box(Modifier.fillMaxSize().background(BackgroundBeige)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                // ── Hero Image with zoom ─────────────────────────────────────
                ZoomableImageSection(
                    imageUrl = art.imageUrl,
                    title = art.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(420.dp)
                )

                // ── Content ──────────────────────────────────────────────────
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            BackgroundBeige,
                            RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                        )
                        .offset(y = (-16).dp)
                        .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 100.dp)
                ) {
                    // Product ID badge + category
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier
                                .background(ContainerGold, RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = art.productId,
                                style = MaterialTheme.typography.labelMedium,
                                color = LuxuryGoldDark,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = art.category,
                            style = MaterialTheme.typography.labelMedium,
                            color = TextSecondary
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    // Title
                    Text(
                        text = art.title,
                        style = MaterialTheme.typography.displayMedium,
                        color = DarkBrown
                    )

                    Spacer(Modifier.height(6.dp))

                    // Artist Name
                    Text(
                        text = "by ${art.artistName}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontStyle = FontStyle.Italic
                        ),
                        color = LuxuryGold
                    )

                    Spacer(Modifier.height(6.dp))

                    // Price
                    Text(
                        text = art.price,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = TerracottaAccent,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(Modifier.height(16.dp))
                    GoldDivider()
                    Spacer(Modifier.height(16.dp))

                    // ── Metadata grid ────────────────────────────────────────
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MetaChip(
                            icon = Icons.Default.WorkspacePremium,
                            label = "Material",
                            value = art.material,
                            modifier = Modifier.weight(1f)
                        )
                        MetaChip(
                            icon = Icons.Default.Straighten,
                            label = "Dimensions",
                            value = art.dimensions,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MetaChip(
                            icon = Icons.Default.Person,
                            label = "Artist",
                            value = art.artistName,
                            modifier = Modifier.weight(1f)
                        )
                        MetaChip(
                            icon = Icons.Default.CalendarMonth,
                            label = "Year",
                            value = art.year,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(Modifier.height(20.dp))

                    // ── Description ──────────────────────────────────────────
                    Text(
                        text = "About this Piece",
                        style = MaterialTheme.typography.headlineSmall,
                        color = DarkBrown
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = art.description,
                        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 26.sp),
                        color = TextPrimary
                    )

                    Spacer(Modifier.height(24.dp))

                    // ── Timeline preview ─────────────────────────────────────
                    GoldDivider(label = "Creation Journey")
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Work in Progress",
                        style = MaterialTheme.typography.headlineSmall,
                        color = DarkBrown
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "See how this sculpture transforms from raw stone to masterpiece",
                        style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                        color = TextSecondary
                    )
                    Spacer(Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = {
                            navController.navigate(Screen.Timeline.createRoute(art.id))
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MediumBrown),
                        border = BorderStroke(1.5.dp, LuxuryGold)
                    ) {
                        Text("View Full Timeline →", fontWeight = FontWeight.Medium)
                    }

                    Spacer(Modifier.height(24.dp))

                    // ── WhatsApp enquiry ─────────────────────────────────────
                    GoldDivider(label = "Enquire")
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Interested in this piece?",
                        style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                        color = TextSecondary,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    WhatsAppEnquireButton(
                        onClick = {
                            openWhatsAppEnquiry(context, art.productId, art.title)
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "We typically respond within 2 hours",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextCaption,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // ── Top navigation bar (floating) ─────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FloatingNavButton(
                    onClick = { navController.popBackStack() },
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
                FloatingNavButton(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, art.title)
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "Check out \"${art.title}\" (${art.productId}) at Shilpa-Kala Showcase! ${art.imageUrl}"
                            )
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                    },
                    icon = Icons.Default.Share,
                    contentDescription = "Share"
                )
            }
        }
    } ?: run {
        // Loading state
        Box(Modifier.fillMaxSize().background(BackgroundBeige), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = LuxuryGold)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ZoomableImageSection(
    imageUrl: String,
    title: String,
    modifier: Modifier = Modifier
) {
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(bottomStart = 0.dp, bottomEnd = 0.dp))
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(1f, 4f)
                    if (scale > 1f) {
                        offsetX += pan.x
                        offsetY += pan.y
                    } else {
                        offsetX = 0f
                        offsetY = 0f
                    }
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(onDoubleTap = {
                    if (scale > 1f) {
                        scale = 1f; offsetX = 0f; offsetY = 0f
                    } else {
                        scale = 2.5f
                    }
                })
            }
    ) {
        GlideImage(
            model = imageUrl,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offsetX,
                    translationY = offsetY
                ),
            transition = CrossFade,
            loading = placeholder { ShimmerBox(Modifier.fillMaxSize()) }
        )
        // Dark gradient at top for back button readability
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(DarkBrown.copy(alpha = 0.5f), Color.Transparent)
                    )
                )
        )
        // Zoom hint
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
                .background(DarkBrown.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text("Pinch to zoom", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.9f))
        }
    }
}

@Composable
private fun MetaChip(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(CardSurface, RoundedCornerShape(12.dp))
            .border(1.dp, DividerColor, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = LuxuryGold, modifier = Modifier.size(14.dp))
            Spacer(Modifier.width(4.dp))
            Text(label, style = MaterialTheme.typography.labelSmall, color = TextCaption)
        }
        Spacer(Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.labelMedium, color = TextPrimary, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun FloatingNavButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(40.dp)
            .background(IvoryWhite.copy(alpha = 0.88f), CircleShape)
    ) {
        Icon(icon, contentDescription = contentDescription, tint = DarkBrown)
    }
}

/**
 * Opens WhatsApp with a prefilled enquiry message including the product ID and artwork title.
 * Shows a toast error if WhatsApp is not installed.
 */
private fun openWhatsAppEnquiry(context: Context, productId: String, title: String) {
    val message = "Hello, I am interested in Product ID: $productId — *$title*. Could you please share more details?"
    val phone = "+919876543210" // placeholder phone number
    try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://wa.me/$phone?text=${Uri.encode(message)}")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "WhatsApp is not installed on this device", Toast.LENGTH_SHORT).show()
    }
}
