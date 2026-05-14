package com.shilpakala.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.shilpakala.navigation.Screen
import com.shilpakala.ui.components.*
import com.shilpakala.ui.theme.*
import com.shilpakala.viewmodel.ArtworkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    navController: NavHostController,
    viewModel: ArtworkViewModel
) {
    val artworks by viewModel.artworks.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    var searchExpanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Scaffold(
        containerColor = BackgroundBeige,
        topBar = {
            GalleryTopBar(
                searchQuery = searchQuery,
                searchExpanded = searchExpanded,
                onSearchExpandToggle = {
                    searchExpanded = !searchExpanded
                    if (!searchExpanded) viewModel.clearSearch()
                },
                onSearchQueryChange = viewModel::updateSearch,
                onSearchClose = {
                    searchExpanded = false
                    viewModel.clearSearch()
                    focusManager.clearFocus()
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    // Skeleton loading grid
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(8) {
                            SkeletonArtworkCard(Modifier.fillMaxWidth())
                        }
                    }
                }
                artworks.isEmpty() -> {
                    EmptyGalleryState()
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(
                            start = 16.dp, end = 16.dp,
                            top = 8.dp, bottom = 80.dp
                        ),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Header spanning full width
                        item(span = { GridItemSpan(2) }) {
                            GalleryHeader(
                                count = artworks.size,
                                categories = categories,
                                selectedCategory = selectedCategory,
                                onCategorySelected = viewModel::selectCategory
                            )
                        }

                        items(artworks, key = { it.id }) { artwork ->
                            ArtworkCard(
                                imageUrl = artwork.imageUrl,
                                title = artwork.title,
                                category = artwork.category,
                                onClick = {
                                    viewModel.selectArtwork(artwork)
                                    navController.navigate(Screen.Detail.createRoute(artwork.id))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItem(fadeInSpec = tween(300))
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GalleryTopBar(
    searchQuery: String,
    searchExpanded: Boolean,
    onSearchExpandToggle: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSearchClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(IvoryWhite, BackgroundBeige)
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Shilpa-Kala",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontFamily = CursiveFamily,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = DarkBrown
                )
                Text(
                    text = "SHOWCASE",
                    style = MaterialTheme.typography.labelMedium.copy(
                        letterSpacing = 4.sp,
                        fontSize = 10.sp
                    ),
                    color = LuxuryGold
                )
            }
            IconButton(
                onClick = onSearchExpandToggle,
                modifier = Modifier
                    .background(ContainerGold, RoundedCornerShape(12.dp))
                    .size(44.dp)
            ) {
                Icon(
                    imageVector = if (searchExpanded) Icons.Default.Close else Icons.Default.Search,
                    contentDescription = "Search",
                    tint = LuxuryGoldDark
                )
            }
        }

        // Search bar
        AnimatedVisibility(
            visible = searchExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                    Text("Search by title, category, material…", color = TextCaption)
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = LuxuryGold)
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = onSearchClose) {
                            Icon(Icons.Default.Close, contentDescription = "Clear", tint = TextSecondary)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = LuxuryGold,
                    unfocusedBorderColor = DividerColor,
                    focusedContainerColor = IvoryWhite,
                    unfocusedContainerColor = IvoryWhite
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { }),
                singleLine = true
            )
        }

        // Gold separator line
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Transparent,
                            LuxuryGold.copy(alpha = 0.4f),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}

@Composable
private fun GalleryHeader(
    count: Int,
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Decorative top accent
        Text(
            text = "✥ ───────── ✥",
            color = LuxuryGold.copy(alpha = 0.6f),
            fontSize = 12.sp,
            letterSpacing = 2.sp
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "Our Collection",
            style = MaterialTheme.typography.headlineMedium,
            color = DarkBrown
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "$count masterpieces · Hoysala Heritage Art",
            style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
            color = TextSecondary
        )
        Spacer(Modifier.height(12.dp))
        // Decorative bottom accent
        Text(
            text = "✥ ───────── ✥",
            color = LuxuryGold.copy(alpha = 0.6f),
            fontSize = 12.sp,
            letterSpacing = 2.sp
        )
        Spacer(Modifier.height(16.dp))

        // Dynamic category chips from ViewModel
        GalleryCategoryChips(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = onCategorySelected
        )
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun GalleryCategoryChips(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.take(5).forEach { category ->
            val isSelected = selectedCategory == category
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = if (isSelected) LuxuryGold else IvoryWhite,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (isSelected) LuxuryGold else DividerColor,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { onCategorySelected(category) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (category.length > 8) category.take(7) + "…" else category,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isSelected) DarkBrown else TextSecondary,
                    maxLines = 1
                )
            }
        }
    }
}
