package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AttarBottomNav
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.ObsidianBlack
import com.example.ui.viewmodel.AttarViewModel
import com.example.ui.viewmodel.NavigationTab

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AttarApp()
            }
        }
    }
}

@Composable
fun AttarApp(
    viewModel: AttarViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle back button behavior
    BackHandler(enabled = uiState.selectedAttarDetail != null || uiState.currentTab != NavigationTab.ATELIER) {
        if (uiState.selectedAttarDetail != null) {
            viewModel.selectAttar(null)
        } else if (uiState.currentTab != NavigationTab.ATELIER) {
            viewModel.setTab(NavigationTab.ATELIER)
        }
    }

    // Trigger snackbar when message changes
    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSnackbar()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .testTag("attar_main_scaffold"),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 80.dp)
            )
        },
        bottomBar = {
            // Hide bottom bar if viewing detail screen
            if (uiState.selectedAttarDetail == null) {
                AttarBottomNav(
                    currentTab = uiState.currentTab,
                    cartItemCount = cartItems.sumOf { it.quantity },
                    onTabSelected = { viewModel.setTab(it) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = if (uiState.selectedAttarDetail == null) innerPadding.calculateBottomPadding() else 0.dp
                )
        ) {
            if (uiState.selectedAttarDetail != null) {
                AttarDetailScreen(
                    attar = uiState.selectedAttarDetail!!,
                    viewModel = viewModel,
                    onBackClick = { viewModel.selectAttar(null) }
                )
            } else {
                when (uiState.currentTab) {
                    NavigationTab.ATELIER -> AtelierHomeScreen(viewModel = viewModel)
                    NavigationTab.ALCHEMIST -> AlchemistStudioScreen(viewModel = viewModel)
                    NavigationTab.QUIZ -> ScentQuizScreen(viewModel = viewModel)
                    NavigationTab.WARDROBE -> WardrobeJournalScreen(viewModel = viewModel)
                    NavigationTab.CART -> CartScreen(viewModel = viewModel)
                }
            }
        }

        // Modals & Sheets
        if (uiState.showCheckoutSheet) {
            CheckoutModalDialog(
                onDismiss = { viewModel.toggleCheckoutSheet(false) },
                onConfirm = { viewModel.completeOrder() }
            )
        }

        if (uiState.orderSuccessId != null) {
            OrderConfirmedDialog(
                orderId = uiState.orderSuccessId!!,
                onDismiss = { viewModel.dismissOrderSuccess() }
            )
        }

        if (uiState.showArticleSheet && uiState.selectedArticle != null) {
            ArticleDialog(
                article = uiState.selectedArticle!!,
                onDismiss = { viewModel.closeArticle() }
            )
        }

        if (uiState.showNewLogSheet) {
            NewScentLogDialog(
                onDismiss = { viewModel.toggleNewLogSheet(false) },
                onSubmit = { attar, occasion, hours, compliments, mood, thoughts ->
                    viewModel.logWear(attar, occasion, hours, compliments, mood, thoughts)
                }
            )
        }
    }
}
