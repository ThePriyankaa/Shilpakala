package com.shilpakala.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.shilpakala.data.model.HeritageSection
import com.shilpakala.ui.components.GoldDivider
import com.shilpakala.ui.components.ShimmerBox
import com.shilpakala.ui.theme.*
import com.shilpakala.viewmodel.ArtworkViewModel

@Composable
fun HeritageScreen(viewModel: ArtworkViewModel) {
    val sections by viewModel.heritageSections.collectAsStateWithLifecycle()
    val categories = listOf("Hoysala Art", "Stone Carving", "Wood Carving", "Bronze Sculptures", "Temple Architecture")
    var selectedCategory by remember { mutableStateOf(categories.first()) }
    val filteredSections = sections.filter { it.category == selectedCategory }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBeige)
            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top)),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // ── Header banner ────────────────────────────────────────────────────
        item {
            HeritageBanner()
        }

        // ── Intro quote ──────────────────────────────────────────────────────
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "\"Stone is the canvas;\npatience is the brush.\"",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontStyle = FontStyle.Italic,
                        lineHeight = 32.sp
                    ),
                    color = DarkBrown
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "— Shilpi Tradition",
                    style = MaterialTheme.typography.labelMedium,
                    color = LuxuryGold
                )
                Spacer(Modifier.height(20.dp))
                GoldDivider()
            }
        }
        
        // ── Category Tabs ────────────────────────────────────────────────────
        item {
            ScrollableTabRow(
                selectedTabIndex = categories.indexOf(selectedCategory),
                containerColor = Color.Transparent,
                contentColor = LuxuryGold,
                edgePadding = 24.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[categories.indexOf(selectedCategory)]),
                        color = LuxuryGold
                    )
                },
                divider = {
                    HorizontalDivider(color = DividerColor)
                }
            ) {
                categories.forEachIndexed { index, category ->
                    Tab(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        text = {
                            Text(
                                text = category,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = if (selectedCategory == category) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedCategory == category) DarkBrown else TextSecondary
                            )
                        }
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
        }

        // ── Heritage sections ────────────────────────────────────────────────
        items(filteredSections, key = { it.id }) { section ->
            HeritageSectionCard(section = section)
        }

        // ── Footer ───────────────────────────────────────────────────────────
        item {
            HeritageFact()
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun HeritageBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        GlideImage(
            model = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/04/Hoysaleswara_Temple%2C_Halebidu.jpg/1280px-Hoysaleswara_Temple%2C_Halebidu.jpg",
            contentDescription = "Hoysala Temple",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            transition = CrossFade,
            loading = placeholder { ShimmerBox(Modifier.fillMaxSize()) }
        )
        // Dark overlay
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, DarkBrown.copy(alpha = 0.75f))
                    )
                )
        )
        // Text overlay
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp)
        ) {
            Text(
                text = "Heritage",
                style = MaterialTheme.typography.displayMedium.copy(letterSpacing = 2.sp),
                color = Color.White
            )
            Text(
                text = "Exploring the Roots of Shilpa-Kala",
                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                color = LuxuryGold.copy(alpha = 0.9f)
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun HeritageSectionCard(section: HeritageSection) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        // Section image
        GlideImage(
            model = section.imageUrl,
            contentDescription = section.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            transition = CrossFade,
            loading = placeholder { ShimmerBox(Modifier.fillMaxSize()) }
        )

        Spacer(Modifier.height(16.dp))

        // Title with gold accent line
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .width(4.dp)
                    .height(28.dp)
                    .background(LuxuryGold, RoundedCornerShape(2.dp))
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = section.title,
                style = MaterialTheme.typography.headlineMedium,
                color = DarkBrown
            )
        }

        Spacer(Modifier.height(8.dp))
        
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(Modifier.background(ContainerGold, RoundedCornerShape(4.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text(section.region, style = MaterialTheme.typography.labelSmall, color = LuxuryGoldDark)
            }
            Box(Modifier.background(ContainerGold, RoundedCornerShape(4.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text(section.historicalPeriod, style = MaterialTheme.typography.labelSmall, color = LuxuryGoldDark)
            }
        }

        Spacer(Modifier.height(12.dp))

        // Content — styled as editorial article
        Text(
            text = section.content,
            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 28.sp),
            color = TextPrimary
        )

        Spacer(Modifier.height(20.dp))
        GoldDivider()
    }
}

@Composable
private fun HeritageFact() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(ContainerGold, RoundedCornerShape(20.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("✦", color = LuxuryGold, fontSize = 24.sp)
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Did You Know?",
            style = MaterialTheme.typography.headlineSmall,
            color = DarkBrown,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "The Hoysaleswara temple at Halebidu was built over 190 years (1121–1311 CE) and features more than 40,000 individually carved sculptures — each one unique.",
            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 24.sp),
            color = DarkBrown
        )
    }
}
