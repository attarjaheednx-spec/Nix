package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CollectionsBookmark
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ScentLog
import com.example.data.model.TolaSize
import com.example.data.repository.AttarRepository
import com.example.ui.components.AttarCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.AttarViewModel

@Composable
fun WardrobeJournalScreen(
    viewModel: AttarViewModel,
    modifier: Modifier = Modifier
) {
    var selectedSubTab by remember { mutableStateOf(0) } // 0: Wardrobe, 1: Journal
    val wishlistIds by viewModel.wishlistIds.collectAsState()
    val customBlends by viewModel.customBlends.collectAsState()
    val scentLogs by viewModel.scentLogs.collectAsState()

    val wishlistedAttars = AttarRepository.curatedCatalog.filter { wishlistIds.contains(it.id) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .testTag("wardrobe_journal_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Switcher: Wardrobe vs Journal
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianCard),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.verticalGradient(
                        listOf(AmberGold.copy(alpha = 0.5f), ObsidianBorder)
                    )
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "PERSONAL CONNOISSEUR ARCHIVE",
                        style = MaterialTheme.typography.labelSmall.copy(
                            letterSpacing = 1.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = AmberGoldLight
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    TabRow(
                        selectedTabIndex = selectedSubTab,
                        containerColor = ObsidianElevated,
                        contentColor = AmberGold,
                        indicator = {},
                        divider = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
                    ) {
                        Tab(
                            selected = selectedSubTab == 0,
                            onClick = { selectedSubTab = 0 },
                            text = {
                                Text(
                                    "Fragrance Wardrobe (${wishlistedAttars.size + customBlends.size})",
                                    fontWeight = if (selectedSubTab == 0) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedSubTab == 0) AmberGoldLight else SandalwoodMuted
                                )
                            },
                            modifier = Modifier.testTag("subtab_wardrobe")
                        )
                        Tab(
                            selected = selectedSubTab == 1,
                            onClick = { selectedSubTab = 1 },
                            text = {
                                Text(
                                    "Scent Journal (${scentLogs.size})",
                                    fontWeight = if (selectedSubTab == 1) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedSubTab == 1) AmberGoldLight else SandalwoodMuted
                                )
                            },
                            modifier = Modifier.testTag("subtab_journal")
                        )
                    }
                }
            }
        }

        if (selectedSubTab == 0) {
            // Fragrance Wardrobe Tab
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.verticalGradient(
                            listOf(AmberGoldDark.copy(alpha = 0.4f), ObsidianBorder)
                        )
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Spa,
                            contentDescription = null,
                            tint = AmberGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Maceration & Cellar Care",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SandalwoodCream
                                )
                            )
                            Text(
                                text = "Store pure oils upright at 18–24°C away from sun. Pure Dehn Al Oud deepens in honeyed facets with every year.",
                                style = MaterialTheme.typography.bodySmall.copy(color = SandalwoodMuted)
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "SAVED ATTAR FLACONS",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = AmberGoldLight,
                        letterSpacing = 1.sp
                    )
                )
            }

            if (wishlistedAttars.isEmpty()) {
                item {
                    Text(
                        text = "Your wardrobe has no saved bottles yet. Browse the Atelier and tap the heart icon to save your favorites.",
                        style = MaterialTheme.typography.bodySmall.copy(color = SandalwoodMuted)
                    )
                }
            } else {
                items(wishlistedAttars, key = { it.id }) { attar ->
                    AttarCard(
                        attar = attar,
                        isWishlisted = true,
                        onCardClick = { viewModel.selectAttar(attar) },
                        onWishlistClick = { viewModel.toggleWishlist(attar.id) },
                        onQuickAddClick = { viewModel.addToCart(attar, TolaSize.SAMPLE) }
                    )
                }
            }
        } else {
            // Scent Journal Tab
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "WEAR HISTORY & COMPLIMENTS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = AmberGoldLight,
                                letterSpacing = 1.sp
                            )
                        )
                        Text(
                            text = "Document how oils react with your skin and climate",
                            style = MaterialTheme.typography.bodySmall.copy(color = SandalwoodMuted)
                        )
                    }

                    Button(
                        onClick = { viewModel.toggleNewLogSheet(true) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AmberGold,
                            contentColor = ObsidianBlack
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("log_scent_button")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Log Scent", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                    }
                }
            }

            if (scentLogs.isEmpty()) {
                item {
                    Text(
                        text = "No journal entries yet. Tap 'Log Scent' to record your first wear experience!",
                        style = MaterialTheme.typography.bodySmall.copy(color = SandalwoodMuted)
                    )
                }
            } else {
                items(scentLogs, key = { it.id }) { log ->
                    ScentLogCard(
                        log = log,
                        onDelete = { viewModel.deleteScentLog(log.id) }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun ScentLogCard(
    log: ScentLog,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("scent_log_${log.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.verticalGradient(
                listOf(AmberGold.copy(alpha = 0.3f), ObsidianBorder)
            )
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = log.attarName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SandalwoodCream
                        )
                    )
                    Text(
                        text = "${log.date} • ${log.occasion}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = AmberGoldLight,
                            fontSize = 11.sp
                        )
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.testTag("delete_log_${log.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Delete entry",
                        tint = RoseOudLight,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = ObsidianBlack,
                    border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
                ) {
                    Text(
                        text = "⏱ Lasted ${log.longevityHours} Hours",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp,
                            color = SandalwoodCream
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = ObsidianBlack,
                    border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
                ) {
                    Text(
                        text = "💬 ${log.compliments} Compliments",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp,
                            color = AmberGoldLight
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            if (log.thoughts.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "\"${log.thoughts}\"",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = SandalwoodMuted,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                )
            }
        }
    }
}
