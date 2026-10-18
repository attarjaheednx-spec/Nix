package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.*
import com.example.data.repository.AttarRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

enum class NavigationTab(val label: String) {
    ATELIER("Atelier"),
    ALCHEMIST("Alchemist"),
    QUIZ("Soul Scent"),
    WARDROBE("Wardrobe"),
    CART("Cart")
}

data class AtelierUiState(
    val currentTab: NavigationTab = NavigationTab.ATELIER,
    val searchQuery: String = "",
    val selectedCategory: AttarCategory = AttarCategory.ALL,
    val selectedAttarDetail: AttarItem? = null,
    val selectedArticle: Article? = null,
    val snackbarMessage: String? = null,
    val showCheckoutSheet: Boolean = false,
    val showNewLogSheet: Boolean = false,
    val showArticleSheet: Boolean = false,
    val orderSuccessId: String? = null
)

class AttarViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AtelierUiState())
    val uiState: StateFlow<AtelierUiState> = _uiState.asStateFlow()

    val wishlistIds = AttarRepository.wishlistIds
    val customBlends = AttarRepository.customBlends
    val scentLogs = AttarRepository.scentLogs
    val cartItems = AttarRepository.cartItems
    val ordersCount = AttarRepository.placedOrdersCount

    // Blending Studio State
    val availableBaseNotes = listOf(
        "Assam Agarwood 12Y",
        "Vintage Mysore Sandalwood",
        "Ambergris Infusion",
        "Kashmiri Botanical Musk",
        "Black Frankincense Resin"
    )
    val availableHeartNotes = listOf(
        "Kannauj Damask Rose",
        "Taif Rose Petals",
        "Jasmine Sambac (Motia)",
        "Spiced Saffron Stigmas",
        "Green Vetiver Roots (Khus)"
    )
    val availableTopNotes = listOf(
        "Bergamot of Calabria",
        "Green Cardamom Pod",
        "Royal Lime & Pine",
        "Pink Pepper Berries",
        "Sparkling Neroli Petals"
    )

    private val _blendBaseNote = MutableStateFlow(availableBaseNotes[0])
    val blendBaseNote: StateFlow<String> = _blendBaseNote.asStateFlow()

    private val _blendBaseDrops = MutableStateFlow(5)
    val blendBaseDrops: StateFlow<Int> = _blendBaseDrops.asStateFlow()

    private val _blendHeartNote = MutableStateFlow(availableHeartNotes[0])
    val blendHeartNote: StateFlow<String> = _blendHeartNote.asStateFlow()

    private val _blendHeartDrops = MutableStateFlow(3)
    val blendHeartDrops: StateFlow<Int> = _blendHeartDrops.asStateFlow()

    private val _blendTopNote = MutableStateFlow(availableTopNotes[0])
    val blendTopNote: StateFlow<String> = _blendTopNote.asStateFlow()

    private val _blendTopDrops = MutableStateFlow(2)
    val blendTopDrops: StateFlow<Int> = _blendTopDrops.asStateFlow()

    private val _blendCustomName = MutableStateFlow("Alchemist Private Reserve")
    val blendCustomName: StateFlow<String> = _blendCustomName.asStateFlow()

    // Quiz State
    private val _quizStep = MutableStateFlow(0)
    val quizStep: StateFlow<Int> = _quizStep.asStateFlow()

    private val _quizSelectedAnswers = MutableStateFlow<Map<Int, AttarCategory>>(emptyMap())
    val quizSelectedAnswers: StateFlow<Map<Int, AttarCategory>> = _quizSelectedAnswers.asStateFlow()

    private val _quizResult = MutableStateFlow<AttarItem?>(null)
    val quizResult: StateFlow<AttarItem?> = _quizResult.asStateFlow()

    val filteredAttars: StateFlow<List<AttarItem>> = combine(
        _uiState.map { it.searchQuery }.distinctUntilChanged(),
        _uiState.map { it.selectedCategory }.distinctUntilChanged()
    ) { query, category ->
        AttarRepository.curatedCatalog.filter { item ->
            val matchesCategory = (category == AttarCategory.ALL || item.category == category)
            val matchesQuery = query.isBlank() ||
                    item.name.contains(query, ignoreCase = true) ||
                    item.arabicName.contains(query, ignoreCase = true) ||
                    item.subtitle.contains(query, ignoreCase = true) ||
                    item.topNotes.any { it.contains(query, ignoreCase = true) } ||
                    item.heartNotes.any { it.contains(query, ignoreCase = true) } ||
                    item.baseNotes.any { it.contains(query, ignoreCase = true) } ||
                    item.origin.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AttarRepository.curatedCatalog)

    fun setTab(tab: NavigationTab) {
        _uiState.update { it.copy(currentTab = tab, selectedAttarDetail = null) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun setCategory(category: AttarCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun selectAttar(attar: AttarItem?) {
        _uiState.update { it.copy(selectedAttarDetail = attar) }
    }

    fun openArticle(article: Article) {
        _uiState.update { it.copy(selectedArticle = article, showArticleSheet = true) }
    }

    fun closeArticle() {
        _uiState.update { it.copy(showArticleSheet = false, selectedArticle = null) }
    }

    fun toggleWishlist(attarId: String) {
        AttarRepository.toggleWishlist(attarId)
        val isAdded = AttarRepository.isWishlisted(attarId)
        showSnackbar(if (isAdded) "Saved to your Fragrance Wardrobe" else "Removed from Wardrobe")
    }

    fun addToCart(attar: AttarItem, size: TolaSize) {
        AttarRepository.addToCart(attar, size)
        showSnackbar("Added ${size.label} of ${attar.name} to cart")
    }

    fun updateCartQuantity(attarId: String, size: TolaSize, delta: Int) {
        AttarRepository.updateCartQuantity(attarId, size, delta)
    }

    fun toggleCheckoutSheet(show: Boolean) {
        _uiState.update { it.copy(showCheckoutSheet = show) }
    }

    fun toggleNewLogSheet(show: Boolean) {
        _uiState.update { it.copy(showNewLogSheet = show) }
    }

    fun completeOrder() {
        val newOrderNumber = "ORD-" + (1000..9999).random()
        AttarRepository.recordOrder()
        _uiState.update { it.copy(showCheckoutSheet = false, orderSuccessId = newOrderNumber) }
        showSnackbar("Order $newOrderNumber confirmed! Prepared in silk velvet box.")
    }

    fun dismissOrderSuccess() {
        _uiState.update { it.copy(orderSuccessId = null) }
    }

    fun showSnackbar(msg: String) {
        _uiState.update { it.copy(snackbarMessage = msg) }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    // Blending studio controls
    fun setBlendBase(note: String) { _blendBaseNote.value = note }
    fun setBlendBaseDrops(drops: Int) { _blendBaseDrops.value = drops.coerceIn(1, 10) }
    fun setBlendHeart(note: String) { _blendHeartNote.value = note }
    fun setBlendHeartDrops(drops: Int) { _blendHeartDrops.value = drops.coerceIn(1, 10) }
    fun setBlendTop(note: String) { _blendTopNote.value = note }
    fun setBlendTopDrops(drops: Int) { _blendTopDrops.value = drops.coerceIn(1, 10) }
    fun setBlendName(name: String) { _blendCustomName.value = name }

    fun saveCurrentBlend() {
        val totalDrops = _blendBaseDrops.value + _blendHeartDrops.value + _blendTopDrops.value
        val harmony = 85 + (totalDrops * 3 % 15)
        val sillageLabel = when {
            _blendBaseDrops.value > 6 -> "Majestic Sillage (Room-filling)"
            _blendHeartDrops.value > 5 -> "Regal Radiance (Arm's length)"
            else -> "Intimate Veil (Skin warmth)"
        }
        val blend = CustomBlend(
            id = UUID.randomUUID().toString(),
            name = _blendCustomName.value.ifBlank { "Bespoke Mukhallat" },
            baseNote = _blendBaseNote.value,
            baseDrops = _blendBaseDrops.value,
            heartNote = _blendHeartNote.value,
            heartDrops = _blendHeartDrops.value,
            topNote = _blendTopNote.value,
            topDrops = _blendTopDrops.value,
            timestamp = System.currentTimeMillis(),
            calculatedSillage = sillageLabel,
            harmonyScore = harmony
        )
        AttarRepository.saveCustomBlend(blend)
        showSnackbar("Recipe \"${blend.name}\" saved to your Atelier formulations!")
    }

    fun deleteCustomBlend(id: String) {
        AttarRepository.deleteCustomBlend(id)
        showSnackbar("Formula removed from Atelier archive")
    }

    // Quiz controls
    fun selectQuizAnswer(questionId: Int, category: AttarCategory) {
        val updated = _quizSelectedAnswers.value.toMutableMap()
        updated[questionId] = category
        _quizSelectedAnswers.value = updated

        if (_quizStep.value < AttarRepository.quizQuestions.size - 1) {
            _quizStep.value += 1
        } else {
            // Calculate best recommendation
            val categoryVotes = updated.values.groupingBy { it }.eachCount()
            val dominantCategory = categoryVotes.maxByOrNull { it.value }?.key ?: AttarCategory.MUKHALLAT
            val match = AttarRepository.curatedCatalog.firstOrNull { it.category == dominantCategory }
                ?: AttarRepository.curatedCatalog.first()
            _quizResult.value = match
        }
    }

    fun resetQuiz() {
        _quizStep.value = 0
        _quizSelectedAnswers.value = emptyMap()
        _quizResult.value = null
    }

    // Scent Log creation
    fun logWear(attarName: String, occasion: String, longevityHours: Int, compliments: Int, mood: String, thoughts: String) {
        val log = ScentLog(
            id = UUID.randomUUID().toString(),
            attarName = attarName,
            date = "Today",
            occasion = occasion.ifBlank { "Daily Signature" },
            longevityHours = longevityHours,
            compliments = compliments,
            mood = mood.ifBlank { "Serene" },
            thoughts = thoughts
        )
        AttarRepository.addScentLog(log)
        _uiState.update { it.copy(showNewLogSheet = false) }
        showSnackbar("Logged wear for $attarName")
    }

    fun deleteScentLog(id: String) {
        AttarRepository.deleteScentLog(id)
        showSnackbar("Journal entry removed")
    }
}
