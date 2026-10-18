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
import androidx.compose.material.icons.outlined.ShoppingBag
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
import androidx.compose.ui.window.Dialog
import com.example.data.model.Article
import com.example.data.model.CartItem
import com.example.data.model.TolaSize
import com.example.data.repository.AttarRepository
import com.example.ui.theme.*
import com.example.ui.viewmodel.AttarViewModel
import com.example.ui.viewmodel.NavigationTab

@Composable
fun CartScreen(
    viewModel: AttarViewModel,
    modifier: Modifier = Modifier
) {
    val cartItems by viewModel.cartItems.collectAsState()
    var promoCode by remember { mutableStateOf("") }
    var promoApplied by remember { mutableStateOf(false) }
    var includeVelvetBox by remember { mutableStateOf(true) }

    val rawSubtotal = cartItems.sumOf { it.unitPrice * it.quantity }
    val discount = if (promoApplied) rawSubtotal * 0.10 else 0.0
    val finalTotal = (rawSubtotal - discount).coerceAtLeast(0.0)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .testTag("cart_screen"),
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
                        imageVector = Icons.Outlined.ShoppingBag,
                        contentDescription = null,
                        tint = AmberGold,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "ROYAL ATELIER CART",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            color = AmberGoldLight
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Your Fragrance Selection",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontFamily = FontFamily.Serif,
                            color = SandalwoodCream,
                            fontSize = 22.sp
                        )
                    )
                    Text(
                        text = "Each crystal flacon is individually filled and inspected by our master distillers before dispatch.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SandalwoodMuted,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }

        if (cartItems.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.ShoppingBag,
                            contentDescription = null,
                            tint = SandalwoodMuted,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Your flacon cart is empty",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = SandalwoodCream
                            )
                        )
                        Text(
                            text = "Explore the pure oils in our Atelier collection",
                            style = MaterialTheme.typography.bodySmall.copy(color = SandalwoodMuted)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.setTab(NavigationTab.ATELIER) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AmberGold,
                                contentColor = ObsidianBlack
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Browse Atelier", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } else {
            // Cart Items List
            items(cartItems) { item ->
                CartItemRow(
                    item = item,
                    onIncrease = { viewModel.updateCartQuantity(item.attarId, item.size, 1) },
                    onDecrease = { viewModel.updateCartQuantity(item.attarId, item.size, -1) }
                )
            }

            // Complimentary Presentation Box Toggle
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.verticalGradient(
                            listOf(AmberGoldDark.copy(alpha = 0.3f), ObsidianBorder)
                        )
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Velvet Gift Presentation Box",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SandalwoodCream
                                )
                            )
                            Text(
                                text = "Complimentary padded silk velvet case with golden seal",
                                style = MaterialTheme.typography.bodySmall.copy(color = SandalwoodMuted)
                            )
                        }
                        Switch(
                            checked = includeVelvetBox,
                            onCheckedChange = { includeVelvetBox = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = ObsidianBlack,
                                checkedTrackColor = AmberGold,
                                uncheckedThumbColor = SandalwoodMuted,
                                uncheckedTrackColor = ObsidianBorder
                            )
                        )
                    }
                }
            }

            // Promo Code Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = ObsidianCard),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.verticalGradient(
                            listOf(AmberGold.copy(alpha = 0.3f), ObsidianBorder)
                        )
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = promoCode,
                            onValueChange = { promoCode = it },
                            placeholder = { Text("Code: SULTAN10", color = SandalwoodMuted) },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = ObsidianElevated,
                                unfocusedContainerColor = ObsidianElevated,
                                focusedBorderColor = AmberGold,
                                unfocusedBorderColor = ObsidianBorder,
                                focusedTextColor = SandalwoodCream,
                                unfocusedTextColor = SandalwoodCream
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("promo_input")
                        )

                        Button(
                            onClick = {
                                if (promoCode.trim().equals("SULTAN10", ignoreCase = true)) {
                                    promoApplied = true
                                    viewModel.showSnackbar("10% Royal Connoisseur discount applied!")
                                } else {
                                    viewModel.showSnackbar("Try promo code: SULTAN10")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (promoApplied) AmberGoldLight else AmberGold,
                                contentColor = ObsidianBlack
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("apply_promo_button")
                        ) {
                            Text(if (promoApplied) "Applied ✓" else "Apply", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Price Summary Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
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
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SummaryRow("Subtotal", "$${rawSubtotal.toInt()}")
                        if (promoApplied) {
                            SummaryRow("Sultan 10% Discount", "-$${discount.toInt()}", AmberGold)
                        }
                        SummaryRow("Courier Dispatch", "Complimentary (Free)", AmberGoldLight)
                        SummaryRow("Insurance & Wax Seal", "Included", SandalwoodMuted)

                        Divider(modifier = Modifier.padding(vertical = 4.dp), color = ObsidianBorder)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "TOTAL",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SandalwoodCream
                                )
                            )
                            Text(
                                text = "$${finalTotal.toInt()}",
                                style = MaterialTheme.typography.displayMedium.copy(
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    color = AmberGold,
                                    fontSize = 24.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = { viewModel.toggleCheckoutSheet(true) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AmberGold,
                                contentColor = ObsidianBlack
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("checkout_button")
                        ) {
                            Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Proceed to Royal Checkout ($${finalTotal.toInt()})",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("cart_item_${item.attarId}_${item.size.name}"),
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
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.arabicName,
                    style = MaterialTheme.typography.bodySmall.copy(color = AmberGold, fontSize = 11.sp)
                )
                Text(
                    text = item.attarName,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = SandalwoodCream
                    )
                )
                Text(
                    text = "${item.size.label} • ${item.size.volume}",
                    style = MaterialTheme.typography.bodySmall.copy(color = SandalwoodMuted, fontSize = 11.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${(item.unitPrice * item.quantity).toInt()}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = AmberGoldLight
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilledTonalIconButton(
                    onClick = onDecrease,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = ObsidianCard,
                        contentColor = SandalwoodCream
                    ),
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(14.dp))
                }

                Text(
                    text = "${item.quantity}",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = SandalwoodCream
                    )
                )

                FilledTonalIconButton(
                    onClick = onIncrease,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = AmberGold,
                        contentColor = ObsidianBlack
                    ),
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(14.dp))
                }
            }
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    valueColor: androidx.compose.ui.graphics.Color = SandalwoodCream
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium.copy(color = SandalwoodMuted))
        Text(text = value, style = MaterialTheme.typography.bodyMedium.copy(color = valueColor, fontWeight = FontWeight.SemiBold))
    }
}

@Composable
fun CheckoutModalDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var recipientName by remember { mutableStateOf("Sayyid Ahmad") }
    var address by remember { mutableStateOf("74 Al-Mansoor Boulevard, Suite 12") }
    var city by remember { mutableStateOf("Dubai / London / New York") }
    var selectedPayment by remember { mutableStateOf("Card") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("checkout_modal"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianCard),
            border = CardDefaults.outlinedCardBorder().copy(
                brush = Brush.verticalGradient(
                    listOf(AmberGold, AmberGoldDark)
                ),
                width = 1.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ROYAL CHECKOUT",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = AmberGold
                        )
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = SandalwoodMuted)
                    }
                }

                Text(
                    text = "Delivery Recipient:",
                    style = MaterialTheme.typography.labelSmall.copy(color = SandalwoodMuted)
                )
                OutlinedTextField(
                    value = recipientName,
                    onValueChange = { recipientName = it },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = ObsidianElevated,
                        unfocusedContainerColor = ObsidianElevated,
                        focusedBorderColor = AmberGold,
                        unfocusedBorderColor = ObsidianBorder,
                        focusedTextColor = SandalwoodCream,
                        unfocusedTextColor = SandalwoodCream
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Shipping Address:",
                    style = MaterialTheme.typography.labelSmall.copy(color = SandalwoodMuted)
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = ObsidianElevated,
                        unfocusedContainerColor = ObsidianElevated,
                        focusedBorderColor = AmberGold,
                        unfocusedBorderColor = ObsidianBorder,
                        focusedTextColor = SandalwoodCream,
                        unfocusedTextColor = SandalwoodCream
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Payment Method:",
                    style = MaterialTheme.typography.labelSmall.copy(color = SandalwoodMuted)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Card", "Apple Pay", "On Delivery").forEach { method ->
                        val isSelected = selectedPayment == method
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedPayment = method },
                            label = { Text(method, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = ObsidianElevated,
                                labelColor = SandalwoodMuted,
                                selectedContainerColor = AmberGold,
                                selectedLabelColor = ObsidianBlack
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AmberGold,
                        contentColor = ObsidianBlack
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("confirm_order_button")
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Seal & Dispatch Order", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ArticleDialog(
    article: Article,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianCard),
            border = CardDefaults.outlinedCardBorder().copy(
                brush = Brush.verticalGradient(listOf(AmberGold, AmberGoldDark))
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ATELIER ESSAY",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = AmberGoldLight,
                            letterSpacing = 1.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = SandalwoodMuted)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = SandalwoodCream
                    )
                )

                Text(
                    text = article.subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(color = AmberGoldLight)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = SandalwoodCream,
                        lineHeight = 22.sp
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AmberGold,
                        contentColor = ObsidianBlack
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Done Reading", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun NewScentLogDialog(
    onDismiss: () -> Unit,
    onSubmit: (String, String, Int, Int, String, String) -> Unit
) {
    var attarName by remember { mutableStateOf("Dehn Al Oud Hindi Qadeem") }
    var occasion by remember { mutableStateOf("Friday Prayer & Feast") }
    var hours by remember { mutableStateOf(24) }
    var compliments by remember { mutableStateOf(3) }
    var mood by remember { mutableStateOf("Majestic & Grounded") }
    var thoughts by remember { mutableStateOf("Warmed gorgeously with pulse points. Highly praised.") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("new_log_dialog"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianCard),
            border = CardDefaults.outlinedCardBorder().copy(
                brush = Brush.verticalGradient(listOf(AmberGold, ObsidianBorder))
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "LOG SCENT WEAR",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = AmberGold
                        )
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = SandalwoodMuted)
                    }
                }

                Text("Attar Worn:", style = MaterialTheme.typography.labelSmall.copy(color = SandalwoodMuted))
                OutlinedTextField(
                    value = attarName,
                    onValueChange = { attarName = it },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Occasion:", style = MaterialTheme.typography.labelSmall.copy(color = SandalwoodMuted))
                OutlinedTextField(
                    value = occasion,
                    onValueChange = { occasion = it },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Hours Lasted:", style = MaterialTheme.typography.labelSmall.copy(color = SandalwoodMuted))
                        OutlinedTextField(
                            value = "$hours",
                            onValueChange = { hours = it.toIntOrNull() ?: 12 },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Compliments:", style = MaterialTheme.typography.labelSmall.copy(color = SandalwoodMuted))
                        OutlinedTextField(
                            value = "$compliments",
                            onValueChange = { compliments = it.toIntOrNull() ?: 0 },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Text("Reflection & Notes:", style = MaterialTheme.typography.labelSmall.copy(color = SandalwoodMuted))
                OutlinedTextField(
                    value = thoughts,
                    onValueChange = { thoughts = it },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = {
                        onSubmit(attarName, occasion, hours, compliments, mood, thoughts)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AmberGold,
                        contentColor = ObsidianBlack
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text("Save to Journal", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun OrderConfirmedDialog(
    orderId: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("order_confirmed_dialog"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianCard),
            border = CardDefaults.outlinedCardBorder().copy(
                brush = Brush.verticalGradient(listOf(AmberGold, AmberGoldDark)),
                width = 1.5.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = AmberGoldContainer,
                    modifier = Modifier.size(56.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = AmberGoldLight,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "ROYAL ORDER SEALED",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        color = AmberGoldLight
                    )
                )

                Text(
                    text = orderId,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = SandalwoodCream
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Your crystal flacons are being hand-poured with traditional glass wands, packaged in our signature padded silk velvet case, and sealed with wax for insured imperial dispatch.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = SandalwoodMuted,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Tracking Steps Preview
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ObsidianElevated, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    TrackingStep("✓ Order Hand-Compounded & Certified", AmberGold)
                    TrackingStep("✓ Sealed in Silk Velvet Presentation Flacon", AmberGold)
                    TrackingStep("● Preparing for Insured Courier Dispatch", SandalwoodCream)
                    TrackingStep("○ Delivery in 2–3 Days", SandalwoodMuted)
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AmberGold,
                        contentColor = ObsidianBlack
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text("Return to Atelier", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun TrackingStep(text: String, color: androidx.compose.ui.graphics.Color) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall.copy(
            color = color,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    )
}
