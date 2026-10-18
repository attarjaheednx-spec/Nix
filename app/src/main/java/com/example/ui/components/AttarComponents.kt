package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AttarItem
import com.example.ui.theme.*
import com.example.ui.viewmodel.NavigationTab

@Composable
fun AttarCard(
    attar: AttarItem,
    isWishlisted: Boolean,
    onCardClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onQuickAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onCardClick)
            .testTag("attar_card_${attar.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = ObsidianCard
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.verticalGradient(
                colors = listOf(
                    AmberGold.copy(alpha = 0.5f),
                    ObsidianBorder
                )
            ),
            width = 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Category, Badges & Wishlist
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = AmberGoldContainer,
                        modifier = Modifier.height(26.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = attar.category.tag,
                                style = MaterialTheme.typography.labelSmall,
                                color = AmberGoldLight,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    if (attar.isRareReserve) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = RoseOudContainer,
                            modifier = Modifier.height(26.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Text(
                                    text = "Reserve",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = RoseOudLight,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                IconButton(
                    onClick = onWishlistClick,
                    modifier = Modifier
                        .size(38.dp)
                        .testTag("wishlist_btn_${attar.id}")
                ) {
                    Icon(
                        imageVector = if (isWishlisted) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Add to Wardrobe",
                        tint = if (isWishlisted) RoseOudLight else SandalwoodMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Arabic Calligraphy Name & English Name
            Text(
                text = attar.arabicName,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily.Serif,
                    fontSize = 18.sp,
                    color = AmberGold
                ),
                letterSpacing = 0.5.sp
            )

            Text(
                text = attar.name,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = SandalwoodCream
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = attar.subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = SandalwoodMuted
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Olfactory Notes preview
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                attar.baseNotes.take(2).forEach { note ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = ObsidianElevated,
                        border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
                    ) {
                        Text(
                            text = note,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = SandalwoodCream,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
                attar.topNotes.take(1).forEach { note ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = ObsidianElevated,
                        border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
                    ) {
                        Text(
                            text = note,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = SandalwoodMuted,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Footer: Longevity metric and Price starting from + quick add
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Longevity",
                        tint = AmberGold,
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        text = "${attar.longevityHours}h longevity",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SandalwoodMuted,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "From $${attar.priceSample.toInt()}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = AmberGoldLight
                            )
                        )
                        Text(
                            text = "1ml sample",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 10.sp,
                                color = SandalwoodMuted
                            )
                        )
                    }

                    FilledTonalIconButton(
                        onClick = onQuickAddClick,
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = AmberGold,
                            contentColor = ObsidianBlack
                        ),
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("quick_add_${attar.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddShoppingCart,
                            contentDescription = "Add Sample to Cart",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OlfactoryPyramidView(
    topNotes: List<String>,
    heartNotes: List<String>,
    baseNotes: List<String>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.linearGradient(
                colors = listOf(AmberGold.copy(alpha = 0.4f), ObsidianBorder)
            ),
            width = 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "OLFACTORY PYRAMID",
                style = MaterialTheme.typography.labelMedium.copy(
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = AmberGold
                )
            )
            Text(
                text = "Pure Oil Evaporation Curve (0% Alcohol)",
                style = MaterialTheme.typography.bodySmall.copy(color = SandalwoodMuted)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // TOP NOTES (Tip of Pyramid)
            PyramidTier(
                tierName = "TOP ACCORDS",
                timeRange = "0 – 45 min",
                notes = topNotes,
                containerColor = AmberGold.copy(alpha = 0.15f),
                borderColor = AmberGold.copy(alpha = 0.4f),
                widthFraction = 0.7f
            )

            Spacer(modifier = Modifier.height(8.dp))

            // HEART NOTES (Center of Pyramid)
            PyramidTier(
                tierName = "HEART / SOUL",
                timeRange = "45 min – 8 hours",
                notes = heartNotes,
                containerColor = RoseOud.copy(alpha = 0.2f),
                borderColor = RoseOudLight.copy(alpha = 0.4f),
                widthFraction = 0.85f
            )

            Spacer(modifier = Modifier.height(8.dp))

            // BASE NOTES (Base of Pyramid)
            PyramidTier(
                tierName = "ANCHOR BASE",
                timeRange = "8 – 36+ hours",
                notes = baseNotes,
                containerColor = ObsidianElevated,
                borderColor = AmberGoldDark.copy(alpha = 0.5f),
                widthFraction = 1f
            )
        }
    }
}

@Composable
private fun PyramidTier(
    tierName: String,
    timeRange: String,
    notes: List<String>,
    containerColor: Color,
    borderColor: Color,
    widthFraction: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(widthFraction)
            .clip(RoundedCornerShape(12.dp))
            .background(containerColor)
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .padding(vertical = 10.dp, horizontal = 12.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tierName,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = AmberGoldLight
                    )
                )
                Text(
                    text = timeRange,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp,
                        color = SandalwoodMuted
                    )
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                modifier = Modifier.fillMaxWidth()
            ) {
                notes.forEach { note ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = ObsidianBlack.copy(alpha = 0.6f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
                    ) {
                        Text(
                            text = note,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = SandalwoodCream,
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LongevityMetricsRow(
    longevityHours: Int,
    sillageLabel: String,
    origin: String,
    macerationAge: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(ObsidianElevated)
            .border(1.dp, ObsidianBorder, RoundedCornerShape(14.dp))
            .padding(vertical = 12.dp, horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MetricItem(
            icon = Icons.Default.Timer,
            label = "Longevity",
            value = "${longevityHours}h Skin"
        )
        Divider(
            modifier = Modifier
                .height(30.dp)
                .width(1.dp),
            color = ObsidianBorder
        )
        MetricItem(
            icon = Icons.Default.WifiTethering,
            label = "Sillage",
            value = sillageLabel.split(" ").firstOrNull() ?: "Regal"
        )
        Divider(
            modifier = Modifier
                .height(30.dp)
                .width(1.dp),
            color = ObsidianBorder
        )
        MetricItem(
            icon = Icons.Default.Verified,
            label = "Purity",
            value = "100% Oil"
        )
        Divider(
            modifier = Modifier
                .height(30.dp)
                .width(1.dp),
            color = ObsidianBorder
        )
        MetricItem(
            icon = Icons.Default.Spa,
            label = "Reserve",
            value = macerationAge.split(" ").take(2).joinToString(" ")
        )
    }
}

@Composable
private fun MetricItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = AmberGold,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = SandalwoodCream,
                fontSize = 11.sp
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = SandalwoodMuted,
                fontSize = 9.sp
            )
        )
    }
}

@Composable
fun AttarBottomNav(
    currentTab: NavigationTab,
    cartItemCount: Int,
    onTabSelected: (NavigationTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .testTag("bottom_nav_bar"),
        containerColor = ObsidianSurface,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = currentTab == NavigationTab.ATELIER,
            onClick = { onTabSelected(NavigationTab.ATELIER) },
            icon = {
                Icon(
                    imageVector = if (currentTab == NavigationTab.ATELIER) Icons.Filled.Storefront else Icons.Outlined.Storefront,
                    contentDescription = "Atelier"
                )
            },
            label = { Text("Atelier") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = ObsidianBlack,
                indicatorColor = AmberGold,
                unselectedIconColor = SandalwoodMuted,
                selectedTextColor = AmberGoldLight,
                unselectedTextColor = SandalwoodMuted
            ),
            modifier = Modifier.testTag("nav_atelier")
        )

        NavigationBarItem(
            selected = currentTab == NavigationTab.ALCHEMIST,
            onClick = { onTabSelected(NavigationTab.ALCHEMIST) },
            icon = {
                Icon(
                    imageVector = if (currentTab == NavigationTab.ALCHEMIST) Icons.Filled.Science else Icons.Outlined.Science,
                    contentDescription = "Alchemist"
                )
            },
            label = { Text("Alchemist") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = ObsidianBlack,
                indicatorColor = AmberGold,
                unselectedIconColor = SandalwoodMuted,
                selectedTextColor = AmberGoldLight,
                unselectedTextColor = SandalwoodMuted
            ),
            modifier = Modifier.testTag("nav_alchemist")
        )

        NavigationBarItem(
            selected = currentTab == NavigationTab.QUIZ,
            onClick = { onTabSelected(NavigationTab.QUIZ) },
            icon = {
                Icon(
                    imageVector = if (currentTab == NavigationTab.QUIZ) Icons.Filled.AutoAwesome else Icons.Outlined.AutoAwesome,
                    contentDescription = "Soul Scent"
                )
            },
            label = { Text("Soul Scent") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = ObsidianBlack,
                indicatorColor = AmberGold,
                unselectedIconColor = SandalwoodMuted,
                selectedTextColor = AmberGoldLight,
                unselectedTextColor = SandalwoodMuted
            ),
            modifier = Modifier.testTag("nav_quiz")
        )

        NavigationBarItem(
            selected = currentTab == NavigationTab.WARDROBE,
            onClick = { onTabSelected(NavigationTab.WARDROBE) },
            icon = {
                Icon(
                    imageVector = if (currentTab == NavigationTab.WARDROBE) Icons.Filled.CollectionsBookmark else Icons.Outlined.CollectionsBookmark,
                    contentDescription = "Wardrobe"
                )
            },
            label = { Text("Wardrobe") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = ObsidianBlack,
                indicatorColor = AmberGold,
                unselectedIconColor = SandalwoodMuted,
                selectedTextColor = AmberGoldLight,
                unselectedTextColor = SandalwoodMuted
            ),
            modifier = Modifier.testTag("nav_wardrobe")
        )

        NavigationBarItem(
            selected = currentTab == NavigationTab.CART,
            onClick = { onTabSelected(NavigationTab.CART) },
            icon = {
                BadgedBox(
                    badge = {
                        if (cartItemCount > 0) {
                            Badge(
                                containerColor = AmberGold,
                                contentColor = ObsidianBlack
                            ) {
                                Text("$cartItemCount")
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (currentTab == NavigationTab.CART) Icons.Filled.ShoppingBag else Icons.Outlined.ShoppingBag,
                        contentDescription = "Cart"
                    )
                }
            },
            label = { Text("Cart") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = ObsidianBlack,
                indicatorColor = AmberGold,
                unselectedIconColor = SandalwoodMuted,
                selectedTextColor = AmberGoldLight,
                unselectedTextColor = SandalwoodMuted
            ),
            modifier = Modifier.testTag("nav_cart")
        )
    }
}
