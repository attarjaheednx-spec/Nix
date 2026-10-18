package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TolaSize
import com.example.data.repository.AttarRepository
import com.example.ui.components.AttarCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.AttarViewModel

@Composable
fun ScentQuizScreen(
    viewModel: AttarViewModel,
    modifier: Modifier = Modifier
) {
    val currentStep by viewModel.quizStep.collectAsState()
    val quizResult by viewModel.quizResult.collectAsState()
    val wishlistIds by viewModel.wishlistIds.collectAsState()
    val questions = AttarRepository.quizQuestions

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .testTag("scent_quiz_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
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
                    Icon(
                        imageVector = Icons.Outlined.AutoAwesome,
                        contentDescription = null,
                        tint = AmberGold,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "SOUL SCENT DIAGNOSTIC",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            color = AmberGoldLight
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Discover Your Signature Oil",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontFamily = FontFamily.Serif,
                            color = SandalwoodCream,
                            fontSize = 22.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Attar is an intimate second skin. Answer 3 intuitive questions to reveal the essence crafted for your soul aura.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SandalwoodMuted,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }

        if (quizResult == null) {
            // Progress Bar
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Question ${currentStep + 1} of ${questions.size}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = AmberGoldLight
                            )
                        )
                        Text(
                            text = "${((currentStep + 1) * 100 / questions.size)}%",
                            style = MaterialTheme.typography.labelSmall.copy(color = SandalwoodMuted)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { (currentStep + 1).toFloat() / questions.size },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp),
                        color = AmberGold,
                        trackColor = ObsidianElevated
                    )
                }
            }

            // Current Question
            val currentQuestion = questions.getOrNull(currentStep)
            if (currentQuestion != null) {
                item {
                    Text(
                        text = currentQuestion.question,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            color = SandalwoodCream
                        )
                    )
                    Text(
                        text = currentQuestion.subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(color = SandalwoodMuted)
                    )
                }

                items(currentQuestion.options) { option ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.selectQuizAnswer(currentQuestion.id, option.matchedCategory)
                            }
                            .testTag("quiz_option_${option.matchedCategory.name}"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = Brush.verticalGradient(
                                listOf(AmberGold.copy(alpha = 0.3f), ObsidianBorder)
                            )
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
                                Text(
                                    text = option.text,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = SandalwoodCream
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = option.description,
                                    style = MaterialTheme.typography.bodySmall.copy(color = SandalwoodMuted)
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = AmberGold,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        } else {
            // Quiz Result Revealed!
            val match = quizResult!!
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = AmberGoldContainer),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.verticalGradient(
                            listOf(AmberGold, AmberGoldDark)
                        ),
                        width = 1.5.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "✨ 98% OLFACTORY HARMONY MATCH ✨",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = AmberGoldLight
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Your Soul Signature:",
                            style = MaterialTheme.typography.bodyMedium.copy(color = SandalwoodCream)
                        )
                        Text(
                            text = match.name,
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontFamily = FontFamily.Serif,
                                color = AmberGold,
                                fontSize = 24.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = match.arabicName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = SandalwoodMuted
                            )
                        )
                    }
                }
            }

            item {
                Text(
                    text = "MATCHING ESSENCE SPECIFICATION",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = AmberGoldLight,
                        letterSpacing = 1.sp
                    )
                )
            }

            item {
                AttarCard(
                    attar = match,
                    isWishlisted = wishlistIds.contains(match.id),
                    onCardClick = { viewModel.selectAttar(match) },
                    onWishlistClick = { viewModel.toggleWishlist(match.id) },
                    onQuickAddClick = { viewModel.addToCart(match, TolaSize.SAMPLE) }
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.resetQuiz() },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = SandalwoodCream
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("retake_quiz_button")
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Retake Quiz")
                    }

                    Button(
                        onClick = { viewModel.selectAttar(match) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AmberGold,
                            contentColor = ObsidianBlack
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("view_matched_attar_button")
                    ) {
                        Text("View Profile", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
