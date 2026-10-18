package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Science
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
import com.example.data.model.CustomBlend
import com.example.ui.theme.*
import com.example.ui.viewmodel.AttarViewModel

@Composable
fun AlchemistStudioScreen(
    viewModel: AttarViewModel,
    modifier: Modifier = Modifier
) {
    val baseNote by viewModel.blendBaseNote.collectAsState()
    val baseDrops by viewModel.blendBaseDrops.collectAsState()
    val heartNote by viewModel.blendHeartNote.collectAsState()
    val heartDrops by viewModel.blendHeartDrops.collectAsState()
    val topNote by viewModel.blendTopNote.collectAsState()
    val topDrops by viewModel.blendTopDrops.collectAsState()
    val customName by viewModel.blendCustomName.collectAsState()
    val savedBlends by viewModel.customBlends.collectAsState()

    val totalDrops = baseDrops + heartDrops + topDrops
    val harmonyScore = (85 + (totalDrops * 3 % 14)).coerceIn(80, 99)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .testTag("alchemist_studio_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Studio Header
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Science,
                            contentDescription = null,
                            tint = AmberGold,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "THE ATTAR ALCHEMIST",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp,
                                color = AmberGoldLight
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Bespoke Compounding Lab",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontFamily = FontFamily.Serif,
                            color = SandalwoodCream,
                            fontSize = 24.sp
                        ),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Formulate your custom pure oil Mukhallat. Adjust drop ratios between anchor woods, floral hearts, and radiant top accords.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SandalwoodMuted,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }

        // Live Compounding Analytics Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.horizontalGradient(
                        listOf(AmberGold.copy(alpha = 0.5f), RoseOudLight.copy(alpha = 0.4f))
                    ),
                    width = 1.dp
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "OLFACTORY HARMONY",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AmberGoldLight,
                                    letterSpacing = 1.sp
                                )
                            )
                            Text(
                                text = "$harmonyScore% Match Index",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AmberGold
                                )
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = AmberGoldContainer,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = "$totalDrops Total Drops",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AmberGoldLight
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Proportion visual bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .weight((baseDrops.toFloat() / totalDrops).coerceAtLeast(0.05f))
                                .fillMaxHeight()
                                .background(AmberGoldDark)
                        )
                        Box(
                            modifier = Modifier
                                .weight((heartDrops.toFloat() / totalDrops).coerceAtLeast(0.05f))
                                .fillMaxHeight()
                                .background(RoseOud)
                        )
                        Box(
                            modifier = Modifier
                                .weight((topDrops.toFloat() / totalDrops).coerceAtLeast(0.05f))
                                .fillMaxHeight()
                                .background(AmberGoldLight)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Base: ${(baseDrops * 100 / totalDrops)}%",
                            style = MaterialTheme.typography.bodySmall.copy(color = AmberGoldDark, fontSize = 11.sp)
                        )
                        Text(
                            text = "Heart: ${(heartDrops * 100 / totalDrops)}%",
                            style = MaterialTheme.typography.bodySmall.copy(color = RoseOudLight, fontSize = 11.sp)
                        )
                        Text(
                            text = "Top: ${(topDrops * 100 / totalDrops)}%",
                            style = MaterialTheme.typography.bodySmall.copy(color = AmberGoldLight, fontSize = 11.sp)
                        )
                    }
                }
            }
        }

        // Section 1: Base Note (Anchor Woods & Resins)
        item {
            NoteCompoundingSection(
                sectionTitle = "1. BASE ACCORD (ANCHOR WOODS & RESINS)",
                sectionSubtitle = "Provides longevity and spiritual depth (8-36h)",
                availableNotes = viewModel.availableBaseNotes,
                selectedNote = baseNote,
                onNoteSelect = { viewModel.setBlendBase(it) },
                drops = baseDrops,
                onDropsChange = { viewModel.setBlendBaseDrops(it) },
                accentColor = AmberGoldDark,
                tagPrefix = "base"
            )
        }

        // Section 2: Heart Note (Bloom & Floral Soul)
        item {
            NoteCompoundingSection(
                sectionTitle = "2. HEART ACCORD (BLOOM & FLORAL SOUL)",
                sectionSubtitle = "Unfolds 45 min after application (10-18h)",
                availableNotes = viewModel.availableHeartNotes,
                selectedNote = heartNote,
                onNoteSelect = { viewModel.setBlendHeart(it) },
                drops = heartDrops,
                onDropsChange = { viewModel.setBlendHeartDrops(it) },
                accentColor = RoseOud,
                tagPrefix = "heart"
            )
        }

        // Section 3: Top Note (Sparkling Volatiles)
        item {
            NoteCompoundingSection(
                sectionTitle = "3. TOP ACCORD (SPARKLING VOLATILES)",
                sectionSubtitle = "Immediate exhilarating opening on skin (0-45m)",
                availableNotes = viewModel.availableTopNotes,
                selectedNote = topNote,
                onNoteSelect = { viewModel.setBlendTop(it) },
                drops = topDrops,
                onDropsChange = { viewModel.setBlendTopDrops(it) },
                accentColor = AmberGoldLight,
                tagPrefix = "top"
            )
        }

        // Formula Naming & Save
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
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "NAME YOUR BESPOKE FORMULATION",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = AmberGold,
                            letterSpacing = 1.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = customName,
                        onValueChange = { viewModel.setBlendName(it) },
                        placeholder = { Text("e.g. Royal Jasmine & Aged Assam Oud", color = SandalwoodMuted) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = ObsidianElevated,
                            unfocusedContainerColor = ObsidianElevated,
                            focusedBorderColor = AmberGold,
                            unfocusedBorderColor = ObsidianBorder,
                            focusedTextColor = SandalwoodCream,
                            unfocusedTextColor = SandalwoodCream
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("blend_name_input")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { viewModel.saveCurrentBlend() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AmberGold,
                                contentColor = ObsidianBlack
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("save_blend_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.BookmarkBorder,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Save to Archive",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }

        // Saved Atelier Formulations Archive
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "SAVED ATELIER FORMULATIONS (${savedBlends.size})",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = SandalwoodCream
                )
            )
        }

        if (savedBlends.isEmpty()) {
            item {
                Text(
                    text = "No custom recipes formulated yet. Blend drops above and save your first signature creation!",
                    style = MaterialTheme.typography.bodySmall.copy(color = SandalwoodMuted)
                )
            }
        } else {
            items(savedBlends, key = { it.id }) { blend ->
                SavedBlendCard(
                    blend = blend,
                    onDelete = { viewModel.deleteCustomBlend(blend.id) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun NoteCompoundingSection(
    sectionTitle: String,
    sectionSubtitle: String,
    availableNotes: List<String>,
    selectedNote: String,
    onNoteSelect: (String) -> Unit,
    drops: Int,
    onDropsChange: (Int) -> Unit,
    accentColor: Color,
    tagPrefix: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.verticalGradient(
                listOf(accentColor.copy(alpha = 0.5f), ObsidianBorder)
            )
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = sectionTitle,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = AmberGoldLight,
                    letterSpacing = 1.sp
                )
            )
            Text(
                text = sectionSubtitle,
                style = MaterialTheme.typography.bodySmall.copy(color = SandalwoodMuted)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Dropdown / Option chips
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                availableNotes.forEach { note ->
                    val isSelected = note == selectedNote
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNoteSelect(note) }
                            .testTag("${tagPrefix}_chip_$note"),
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) AmberGoldContainer else ObsidianElevated,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) AmberGold else ObsidianBorder
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = note,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) AmberGoldLight else SandalwoodCream
                                )
                            )
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = AmberGold,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Drops Stepper
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Proportion Drops: $drops drops",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = SandalwoodCream
                    )
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledTonalIconButton(
                        onClick = { onDropsChange(drops - 1) },
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = ObsidianElevated,
                            contentColor = SandalwoodCream
                        ),
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("${tagPrefix}_drops_minus")
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease drops", modifier = Modifier.size(16.dp))
                    }

                    Text(
                        text = "$drops",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = AmberGold
                        )
                    )

                    FilledTonalIconButton(
                        onClick = { onDropsChange(drops + 1) },
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = AmberGold,
                            contentColor = ObsidianBlack
                        ),
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("${tagPrefix}_drops_plus")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Increase drops", modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun SavedBlendCard(
    blend: CustomBlend,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("saved_blend_${blend.id}"),
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
                        text = blend.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SandalwoodCream
                        )
                    )
                    Text(
                        text = "${blend.calculatedSillage} • ${blend.harmonyScore}% Harmony",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = AmberGoldLight,
                            fontSize = 11.sp
                        )
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.testTag("delete_blend_${blend.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Delete Formula",
                        tint = RoseOudLight,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                FormulaPill("Base: ${blend.baseNote} (${blend.baseDrops}d)", AmberGoldDark)
                FormulaPill("Heart: ${blend.heartNote} (${blend.heartDrops}d)", RoseOud)
                FormulaPill("Top: ${blend.topNote} (${blend.topDrops}d)", AmberGoldLight)
            }
        }
    }
}

@Composable
private fun FormulaPill(label: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = ObsidianBlack,
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.4f))
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 10.sp,
                color = SandalwoodCream
            ),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
        )
    }
}
