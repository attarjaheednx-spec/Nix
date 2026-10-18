package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Article
import com.example.data.model.AttarCategory
import com.example.data.model.AttarItem
import com.example.data.model.TolaSize
import com.example.data.repository.AttarRepository
import com.example.ui.components.AttarCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.AttarViewModel
import com.example.ui.viewmodel.NavigationTab

@Composable
fun AtelierHomeScreen(
    viewModel: AttarViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val attars by viewModel.filteredAttars.collectAsState()
    val wishlistIds by viewModel.wishlistIds.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .testTag("atelier_home_screen"),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Hero Banner with Generated Artisanal Perfume Artwork
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            ) {
                // Background image
                Image(
                    painter = painterResource(id = R.drawable.ic_attar_hero),
                    contentDescription = "Attar Laboratory Still Life",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Luxurious gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    ObsidianBlack.copy(alpha = 0.5f),
                                    ObsidianBlack
                                )
                            )
                        )
                )

                // Hero content
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = AmberGoldContainer.copy(alpha = 0.9f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AmberGold.copy(alpha = 0.6f))
                    ) {
                        Text(
                            text = "100% PURE CONCENTRATED OILS • ZERO ALCOHOL",
                            style = MaterialTheme.typography.labelSmall.copy(
                                letterSpacing = 1.2.sp,
                                fontWeight = FontWeight.Bold,
                                color = AmberGoldLight
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "ATTAR ATELIER",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontFamily = FontFamily.Serif,
                            color = SandalwoodCream,
                            letterSpacing = 2.sp
                        )
                    )

                    Text(
                        text = "Traditional Hydro-distilled Perfumery & Pure Dehn Al Oud",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = SandalwoodMuted
                        )
                    )
                }
            }
        }

        // Search Bar
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    placeholder = {
                        Text(
                            "Search by note (Oud, Rose, Saffron, Khus...)",
                            color = SandalwoodMuted,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search",
                            tint = AmberGold
                        )
                    },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotBlank()) {
                            IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear",
                                    tint = SandalwoodMuted
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = ObsidianSurface,
                        unfocusedContainerColor = ObsidianSurface,
                        focusedBorderColor = AmberGold,
                        unfocusedBorderColor = ObsidianBorder,
                        focusedTextColor = SandalwoodCream,
                        unfocusedTextColor = SandalwoodCream
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_bar")
                )
            }
        }

        // Category Filter Chips
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(AttarCategory.values()) { category ->
                    val isSelected = uiState.selectedCategory == category
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setCategory(category) },
                        label = {
                            Text(
                                text = category.displayName,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = ObsidianElevated,
                            labelColor = SandalwoodMuted,
                            selectedContainerColor = AmberGold,
                            selectedLabelColor = ObsidianBlack
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = ObsidianBorder,
                            selectedBorderColor = AmberGold,
                            enabled = true,
                            selected = isSelected
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("filter_chip_${category.name}")
                    )
                }
            }
        }

        // Soul Scent Diagnostic Callout Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .clickable { viewModel.setTab(NavigationTab.QUIZ) }
                    .testTag("quiz_callout_banner"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.horizontalGradient(
                        colors = listOf(AmberGold.copy(alpha = 0.5f), RoseOudLight.copy(alpha = 0.3f))
                    ),
                    width = 1.dp
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.AutoAwesome,
                                contentDescription = null,
                                tint = AmberGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "SOUL SCENT DIAGNOSTIC",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp,
                                    color = AmberGoldLight
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Unsure which attar suits you?",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = SandalwoodCream
                            )
                        )
                        Text(
                            text = "Take the 1-minute Olfactory Diagnostic to discover your personal signature essence.",
                            style = MaterialTheme.typography.bodySmall.copy(color = SandalwoodMuted)
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Start Quiz",
                        tint = AmberGold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        // Section Title: Curated Attars
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${uiState.selectedCategory.displayName} (${attars.size})",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = SandalwoodCream
                    )
                )

                Text(
                    text = "Pure Oil",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = AmberGold
                    )
                )
            }
        }

        // Attar Items List
        if (attars.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Spa,
                            contentDescription = null,
                            tint = SandalwoodMuted,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No attars found matching \"${uiState.searchQuery}\"",
                            style = MaterialTheme.typography.bodyMedium.copy(color = SandalwoodMuted)
                        )
                    }
                }
            }
        } else {
            items(attars, key = { it.id }) { attar ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    AttarCard(
                        attar = attar,
                        isWishlisted = wishlistIds.contains(attar.id),
                        onCardClick = { viewModel.selectAttar(attar) },
                        onWishlistClick = { viewModel.toggleWishlist(attar.id) },
                        onQuickAddClick = { viewModel.addToCart(attar, TolaSize.SAMPLE) }
                    )
                }
            }
        }

        // Connoisseur Stories / Educational Carousel
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "THE ART OF ATTAR",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = AmberGoldLight
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Text(
                    text = "Ancient wisdom of deg, bhapka, and oil maturation",
                    style = MaterialTheme.typography.bodySmall.copy(color = SandalwoodMuted),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(AttarRepository.knowledgeArticles) { article ->
                        KnowledgeCard(
                            article = article,
                            onClick = { viewModel.openArticle(article) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun KnowledgeCard(
    article: Article,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .clickable(onClick = onClick)
            .testTag("article_card_${article.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.verticalGradient(
                listOf(AmberGoldDark.copy(alpha = 0.4f), ObsidianBorder)
            ),
            width = 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "ESSAY",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = AmberGold,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
                Text(
                    text = article.readTime,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp,
                        color = SandalwoodMuted
                    )
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = SandalwoodCream
                ),
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = article.subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = SandalwoodMuted,
                    fontSize = 11.sp
                ),
                maxLines = 2
            )
        }
    }
}
