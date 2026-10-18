package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AttarItem
import com.example.data.model.TolaSize
import com.example.ui.components.LongevityMetricsRow
import com.example.ui.components.OlfactoryPyramidView
import com.example.ui.theme.*
import com.example.ui.viewmodel.AttarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttarDetailScreen(
    attar: AttarItem,
    viewModel: AttarViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSize by remember { mutableStateOf(TolaSize.SAMPLE) }
    val wishlistIds by viewModel.wishlistIds.collectAsState()
    val isWishlisted = wishlistIds.contains(attar.id)

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .testTag("attar_detail_screen"),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = attar.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SandalwoodCream
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("detail_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = AmberGold
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.toggleWishlist(attar.id) },
                        modifier = Modifier.testTag("detail_wishlist_button")
                    ) {
                        Icon(
                            imageVector = if (isWishlisted) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isWishlisted) RoseOudLight else SandalwoodMuted
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ObsidianSurface,
                    titleContentColor = SandalwoodCream
                )
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars),
                color = ObsidianSurface,
                tonalElevation = 8.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "TOTAL",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SandalwoodMuted,
                                fontSize = 10.sp
                            )
                        )
                        Text(
                            text = "$${attar.getPriceForSize(selectedSize).toInt()}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = AmberGoldLight
                            )
                        )
                        Text(
                            text = selectedSize.volume,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = SandalwoodMuted
                            )
                        )
                    }

                    Button(
                        onClick = { viewModel.addToCart(attar, selectedSize) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AmberGold,
                            contentColor = ObsidianBlack
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .testTag("add_to_cart_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Add to Cart",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(ObsidianBlack),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Ornament & Arabic Name
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = ObsidianCard),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.verticalGradient(
                            listOf(AmberGold.copy(alpha = 0.6f), ObsidianBorder)
                        ),
                        width = 1.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = attar.arabicName,
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontFamily = FontFamily.Serif,
                                color = AmberGold
                            ),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = attar.name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = SandalwoodCream
                            ),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = attar.subtitle,
                            style = MaterialTheme.typography.bodyMedium.copy(color = SandalwoodMuted),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = AmberGoldContainer
                            ) {
                                Text(
                                    text = "★ ${attar.rating} (${attar.reviewsCount} reviews)",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = AmberGoldLight,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = ObsidianElevated,
                                border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
                            ) {
                                Text(
                                    text = attar.macerationAge,
                                    style = MaterialTheme.typography.labelSmall.copy(color = SandalwoodCream),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Tola Size Selector
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "SELECT BOTTLE SIZE & PACKAGING",
                        style = MaterialTheme.typography.labelMedium.copy(
                            letterSpacing = 1.sp,
                            fontWeight = FontWeight.Bold,
                            color = AmberGold
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TolaSize.values().forEach { size ->
                            val isSelected = selectedSize == size
                            val price = attar.getPriceForSize(size)

                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedSize = size }
                                    .testTag("size_option_${size.name}"),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) AmberGoldContainer else ObsidianElevated
                                ),
                                border = CardDefaults.outlinedCardBorder().copy(
                                    brush = if (isSelected) Brush.verticalGradient(
                                        listOf(AmberGold, AmberGoldDark)
                                    ) else Brush.verticalGradient(
                                        listOf(ObsidianBorder, ObsidianBorder)
                                    ),
                                    width = if (isSelected) 1.5.dp else 1.dp
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = size.label,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) AmberGoldLight else SandalwoodCream
                                        ),
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = size.volume,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontSize = 10.sp,
                                            color = SandalwoodMuted
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "$${price.toInt()}",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) AmberGold else SandalwoodCream
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Longevity & Sillage Metrics
            item {
                LongevityMetricsRow(
                    longevityHours = attar.longevityHours,
                    sillageLabel = attar.sillage.label,
                    origin = attar.origin,
                    macerationAge = attar.macerationAge
                )
            }

            // Olfactory Notes Pyramid
            item {
                OlfactoryPyramidView(
                    topNotes = attar.topNotes,
                    heartNotes = attar.heartNotes,
                    baseNotes = attar.baseNotes
                )
            }

            // Provenance & Distillation Details
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ObsidianCard),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.verticalGradient(
                            listOf(ObsidianBorder, ObsidianBorder)
                        )
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Public,
                                contentDescription = null,
                                tint = AmberGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ORIGIN & DISTILLATION PROVENANCE",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AmberGoldLight,
                                    letterSpacing = 1.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Origin: ${attar.origin}",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = SandalwoodCream
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = attar.distillationMethod,
                            style = MaterialTheme.typography.bodyMedium.copy(color = SandalwoodMuted)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Master Distiller's Tasting Notes:",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = AmberGold
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = attar.tastingNotes,
                            style = MaterialTheme.typography.bodyMedium.copy(color = SandalwoodCream)
                        )
                    }
                }
            }

            // Sacred Application Ritual Guide
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.verticalGradient(
                            listOf(RoseOudLight.copy(alpha = 0.4f), ObsidianBorder)
                        )
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.TouchApp,
                                contentDescription = null,
                                tint = RoseOudLight,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "TRADITIONAL GLASS WAND RITUAL",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = RoseOudLight,
                                    letterSpacing = 1.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = attar.applicationRitual,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = SandalwoodCream,
                                lineHeight = 20.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Best Occasions: ${attar.occasions.joinToString(" • ")}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = AmberGoldLight,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
    }
}
