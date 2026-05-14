package com.shilpakala.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
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
import com.shilpakala.data.model.TimelineStage
import com.shilpakala.ui.components.GoldDivider
import com.shilpakala.ui.components.ShimmerBox
import com.shilpakala.ui.theme.*
import com.shilpakala.viewmodel.ArtworkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScreen(
    artworkId: String,
    navController: NavHostController,
    viewModel: ArtworkViewModel
) {
    // Load timeline data through ViewModel
    LaunchedEffect(artworkId) {
        if (artworkId != "global") {
            viewModel.selectArtworkById(artworkId)
        } else {
            viewModel.loadTimelineForArtwork(
                viewModel.artworks.value.firstOrNull()?.id ?: "1"
            )
        }
    }

    val artwork by viewModel.selectedArtwork.collectAsStateWithLifecycle()
    val stages by viewModel.timelineStages.collectAsStateWithLifecycle()
    val artworkTitle = artwork?.title ?: "Stone Sculpture"

    Scaffold(
        containerColor = BackgroundBeige,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Creation Journey",
                            style = MaterialTheme.typography.titleLarge,
                            color = DarkBrown
                        )
                        Text(
                            artworkTitle,
                            style = MaterialTheme.typography.labelMedium.copy(fontSize = 11.sp),
                            color = TextSecondary,
                            maxLines = 1
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = DarkBrown
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = IvoryWhite)
            )
        }
    ) { paddingValues ->
        if (stages.isEmpty()) {
            // Loading / empty state
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = LuxuryGold)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                // Header
                item {
                    Column {
                        Text(
                            "From Stone to Masterpiece",
                            style = MaterialTheme.typography.displaySmall,
                            color = DarkBrown
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Witness the transformation across ${stages.size} stages",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                        Spacer(Modifier.height(4.dp))
                        artwork?.artistName?.let { name ->
                            Text(
                                text = "Artisan: $name",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontStyle = FontStyle.Italic
                                ),
                                color = LuxuryGold
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                        GoldDivider()
                        Spacer(Modifier.height(8.dp))
                    }
                }

                itemsIndexed(stages, key = { _, s -> s.id }) { index, stage ->
                    TimelineStageCard(
                        stage = stage,
                        index = index,
                        isLast = index == stages.lastIndex
                    )
                }
            }
        }
    }
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
