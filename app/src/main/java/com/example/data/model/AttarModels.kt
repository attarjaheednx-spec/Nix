package com.example.data.model

enum class AttarCategory(val displayName: String, val tag: String) {
    ALL("All Attars", "All"),
    OUD("Dehn Al Oud", "Oud"),
    FLORAL("Ruh & Florals", "Floral"),
    MUKHALLAT("Royal Blends", "Mukhallat"),
    AMBER_MUSK("Amber & Musk", "Amber"),
    SACRED("Sacred Incense", "Incense")
}

enum class SillageLevel(val label: String, val description: String) {
    INTIMATE("Intimate Veil", "Whispers close to skin, sensual personal aura"),
    MODERATE("Dignified Halo", "Noticed at arms length, elegant poise"),
    REGAL("Regal Radiance", "Envelops room gently without alcohol harshness"),
    ROOM_FILLING("Majestic Sillage", "Legendary trailing aura that lingers for hours")
}

enum class TolaSize(val label: String, val volume: String) {
    SAMPLE("Sample Vial", "1ml (0.08 Tola)"),
    HALF_TOLA("Half Tola", "6ml (0.5 Tola)"),
    FULL_TOLA("Crystal Flacon", "12ml (1.0 Tola)")
}

data class AttarItem(
    val id: String,
    val name: String,
    val arabicName: String,
    val subtitle: String,
    val category: AttarCategory,
    val priceSample: Double,
    val priceHalfTola: Double,
    val priceFullTola: Double,
    val topNotes: List<String>,
    val heartNotes: List<String>,
    val baseNotes: List<String>,
    val origin: String,
    val distillationMethod: String,
    val longevityHours: Int,
    val sillage: SillageLevel,
    val description: String,
    val tastingNotes: String,
    val applicationRitual: String,
    val occasions: List<String>,
    val season: String,
    val rating: Float,
    val reviewsCount: Int,
    val isBestseller: Boolean = false,
    val isRareReserve: Boolean = false,
    val macerationAge: String
) {
    fun getPriceForSize(size: TolaSize): Double = when (size) {
        TolaSize.SAMPLE -> priceSample
        TolaSize.HALF_TOLA -> priceHalfTola
        TolaSize.FULL_TOLA -> priceFullTola
    }
}

data class CustomBlend(
    val id: String,
    val name: String,
    val baseNote: String,
    val baseDrops: Int,
    val heartNote: String,
    val heartDrops: Int,
    val topNote: String,
    val topDrops: Int,
    val timestamp: Long,
    val calculatedSillage: String,
    val harmonyScore: Int
)

data class ScentLog(
    val id: String,
    val attarName: String,
    val date: String,
    val occasion: String,
    val longevityHours: Int,
    val compliments: Int,
    val mood: String,
    val thoughts: String
)

data class CartItem(
    val attarId: String,
    val attarName: String,
    val arabicName: String,
    val size: TolaSize,
    val unitPrice: Double,
    val quantity: Int
)

data class QuizOption(
    val text: String,
    val description: String,
    val matchedCategory: AttarCategory
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val subtitle: String,
    val options: List<QuizOption>
)

data class Article(
    val id: String,
    val title: String,
    val subtitle: String,
    val readTime: String,
    val content: String
)
