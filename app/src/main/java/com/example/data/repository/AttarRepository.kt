package com.example.data.repository

import com.example.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

object AttarRepository {

    val curatedCatalog: List<AttarItem> = listOf(
        AttarItem(
            id = "oud-hindi-qadeem",
            name = "Dehn Al Oud Hindi Qadeem",
            arabicName = "دهن العود الهندي القديم",
            subtitle = "12-Year Aged Assam Wild Agarwood",
            category = AttarCategory.OUD,
            priceSample = 24.0,
            priceHalfTola = 135.0,
            priceFullTola = 240.0,
            topNotes = listOf("Aged Leather", "Dried Hay", "Golden Saffron"),
            heartNotes = listOf("Barnyard Sweet Honey", "Warm Earth", "Smoked Cedar"),
            baseNotes = listOf("Deep Resinous Agarwood", "Balsamic Amber", "Dark Cocoa"),
            origin = "Upper Assam, India",
            distillationMethod = "Hydro-distilled in traditional Copper Degs, aged 12 years in darkened glass carboys",
            longevityHours = 32,
            sillage = SillageLevel.ROOM_FILLING,
            description = "A crown jewel for authentic oud collectors. Harvested from wild Aquilaria agallocha trees over 60 years old. Starts with a commanding, dignified leathery warmth that slowly transmutes into rich honeyed wood and hypnotic balsamic smoke.",
            tastingNotes = "Dense dark molasses hue with viscous slow tear drops on glass. Zero sharp alcohol burn.",
            applicationRitual = "Touch the tip of the glass rod to your inner wrists. Press without rubbing, then gently tap behind ear lobes and the nape of your collar.",
            occasions = listOf("Royal Galas", "Jumu'ah Prayer", "Winter Evenings", "Meditation"),
            season = "Autumn & Winter",
            rating = 4.95f,
            reviewsCount = 142,
            isBestseller = true,
            isRareReserve = true,
            macerationAge = "Vintage Aged 12 Years"
        ),
        AttarItem(
            id = "ruh-gulab-kannauj",
            name = "Ruh Gulab (Damask Rose)",
            arabicName = "روح الورد القنوجي",
            subtitle = "Pure Hydro-distilled Damask Petals",
            category = AttarCategory.FLORAL,
            priceSample = 18.0,
            priceHalfTola = 95.0,
            priceFullTola = 175.0,
            topNotes = listOf("Dewy Pink Rose Petals", "Green Stem", "Sweet Apple Blossom"),
            heartNotes = listOf("Deep Crimson Damascena", "Honey Dew", "Soft Spices"),
            baseNotes = listOf("Vintage Mysore Sandalwood Base", "Clean White Musk"),
            origin = "Kannauj (The Perfume Capital), India",
            distillationMethod = "Traditional Deg & Bhapka steam condensation over pure Santalum Album sandalwood base",
            longevityHours = 18,
            sillage = SillageLevel.REGAL,
            description = "Over four metric tons of handpicked Rosa damascena blossoms collected at dawn yield merely a single tola of this pure celestial essence. It unfolds with the exhilarating freshness of dew-kissed petals before settling into an intoxicating, warm honeyed rose.",
            tastingNotes = "Pale golden amber with radiant luminous clarity. Intensely floral and spiritually uplifting.",
            applicationRitual = "Apply a minute droplet between your palms, inhale deeply three times, and gently stroke through beard or hair ends.",
            occasions = listOf("Daily Elegance", "Spiritual Dhikr", "Weddings", "Spring Afternoon"),
            season = "All Seasons (Excels in Spring/Summer)",
            rating = 4.90f,
            reviewsCount = 98,
            isBestseller = true,
            isRareReserve = false,
            macerationAge = "Spring 2024 Distillation"
        ),
        AttarItem(
            id = "mukhallat-al-sultan",
            name = "Mukhallat Al-Sultan",
            arabicName = "مخلط السلطان الفاخر",
            subtitle = "The Imperial Royal Signature Blend",
            category = AttarCategory.MUKHALLAT,
            priceSample = 22.0,
            priceHalfTola = 120.0,
            priceFullTola = 210.0,
            topNotes = listOf("Taif Rose", "Royal Saffron", "Bergamot Zest"),
            heartNotes = listOf("Ambergris Infusion", "Cardamom Seed", "Jasmine Sambac"),
            baseNotes = listOf("Cambodian Agarwood", "Aged Sandalwood", "Velvet Musk"),
            origin = "Mecca & Kannauj Atelier",
            distillationMethod = "Bespoke proprietary co-maceration aged 36 months in Spanish oak casks",
            longevityHours = 26,
            sillage = SillageLevel.REGAL,
            description = "A masterwork created for connoisseurs who demand prestige and opulence. Blends the ethereal sweetness of rare mountain Taif roses with rich Cambodian agarwood oil and golden oceanic ambergris, creating an imperial presence.",
            tastingNotes = "Warm cognac color. Incomparable balance of floral majesty, spicy warmth, and aristocratic wood.",
            applicationRitual = "Draw a single thin stroke on the pulse point of the neck and the chest where body heat continuously projects the heart notes.",
            occasions = listOf("Formal Receptions", "Celebrations", "Signature Daily Wear"),
            season = "Year-Round Signature",
            rating = 4.98f,
            reviewsCount = 215,
            isBestseller = true,
            isRareReserve = true,
            macerationAge = "Macerated 3 Years"
        ),
        AttarItem(
            id = "ruh-khus-mitti",
            name = "Ruh Khus (Green Vetiver)",
            arabicName = "روح الخس النقي",
            subtitle = "Wild Roots Hydro-distilled over Baked Earth",
            category = AttarCategory.FLORAL,
            priceSample = 16.0,
            priceHalfTola = 80.0,
            priceFullTola = 145.0,
            topNotes = listOf("Petrichor (First Monsoon Rain)", "Crushed Green Blades", "Cool Ozone"),
            heartNotes = listOf("Smoky Vetiver Roots", "Sweet Baked Clay", "Earthy Moss"),
            baseNotes = listOf("Dry Sandalwood", "Root Amber", "Riverbed Stone"),
            origin = "Bhadohi & Kannauj, India",
            distillationMethod = "Copper Deg hydro-distillation of wild riverbed Chrysopogon zizanioides roots",
            longevityHours = 16,
            sillage = SillageLevel.MODERATE,
            description = "The emerald spirit of nature. Ruh Khus possesses a mesmerizing natural emerald green hue derived from the copper still reactions. It smells of baked summer earth receiving the very first drops of torrential monsoon rains—deeply cooling and calming.",
            tastingNotes = "Vibrant natural emerald green oil. Incredibly cooling on the skin during scorching heat.",
            applicationRitual = "Best applied to the temples and wrists on hot summer days. Its natural cooling therapeutic properties quiet the mind.",
            occasions = listOf("Meditation", "Hot Summer Days", "Office Wear", "Pre-Sleep Relaxation"),
            season = "Summer & Monsoon",
            rating = 4.88f,
            reviewsCount = 84,
            isBestseller = false,
            isRareReserve = false,
            macerationAge = "Distilled June 2024"
        ),
        AttarItem(
            id = "musk-ghazal-kashmiri",
            name = "Musk Ghazal Kashmiri",
            arabicName = "مسك الغزال الكشميري",
            subtitle = "Velvety Botanical Musk & Saffron",
            category = AttarCategory.AMBER_MUSK,
            priceSample = 20.0,
            priceHalfTola = 110.0,
            priceFullTola = 195.0,
            topNotes = listOf("Kashmiri Saffron", "Sweet Almond", "Nutmeg"),
            heartNotes = listOf("Velvet Botanical Musk", "Warm Tonka", "Vanilla Pod"),
            baseNotes = listOf("Mysore Sandalwood", "Atlas Cedar", "Warm Amber Resin"),
            origin = "Srinagar & Kannauj",
            distillationMethod = "Cruelty-free botanical musk mallow (Ambrette seeds) compound infused in sandalwood",
            longevityHours = 24,
            sillage = SillageLevel.INTIMATE,
            description = "A warm, comforting, and deeply intimate elixir. Formulated using pure botanical ambrette seeds and warm saffron strands steeped in aged sandalwood. It transforms into an angelic second-skin warmth that beckons closer inspection.",
            tastingNotes = "Rich amber-gold with silky glide. Becomes creamier and more powdery as the day evolves.",
            applicationRitual = "Apply generously to the pulse points at the collarbone and wrists for an intimate lingering halo.",
            occasions = listOf("Intimate Evenings", "Everyday Soft Luxury", "Prayer"),
            season = "Autumn & Winter",
            rating = 4.92f,
            reviewsCount = 112,
            isBestseller = false,
            isRareReserve = true,
            macerationAge = "Aged 4 Years"
        ),
        AttarItem(
            id = "kalakassi-oud-reserve",
            name = "Kalakassi Oud Reserve",
            arabicName = "كلاكاسي عود ملكي خاص",
            subtitle = "Cambodian Wild Forest 25-Year Wood",
            category = AttarCategory.OUD,
            priceSample = 28.0,
            priceHalfTola = 160.0,
            priceFullTola = 290.0,
            topNotes = listOf("Dried Dates", "Dark Wild Honey", "Ripe Figs"),
            heartNotes = listOf("Pecan Praline", "Sweet Incense Smoke", "Warm Cinnamon"),
            baseNotes = listOf("Ancient Resinous Agarwood", "Smoked Vetiver", "Sweet Labdanum"),
            origin = "Koh Kong, Cambodia",
            distillationMethod = "Artisanal slow steam distillation under low pressure, zero fermentation",
            longevityHours = 36,
            sillage = SillageLevel.ROOM_FILLING,
            description = "For those who prefer their oud without barnyard pungency, Kalakassi is the holy grail. Bursting with sweet dried fruit, dark honey, and sacred temple incense, this is a smooth, intoxicating journey into ancient Cambodian rain forests.",
            tastingNotes = "Thick, syrupy dark mahogany texture. The sillage creates a royal trail wherever you walk.",
            applicationRitual = "A single bead on the glass rod is enough for 24+ hours of opulent fragrant projection.",
            occasions = listOf("Weddings", "Royal Banquets", "Collectors Evenings"),
            season = "Autumn, Winter & Cool Evenings",
            rating = 4.99f,
            reviewsCount = 167,
            isBestseller = true,
            isRareReserve = true,
            macerationAge = "Vintage Reserve 8 Years"
        ),
        AttarItem(
            id = "shamama-tul-amber",
            name = "Shamama Tul Amber",
            arabicName = "شمامة العنبر الأصيلة",
            subtitle = "Ancient 40-Herb Spice Infusion",
            category = AttarCategory.AMBER_MUSK,
            priceSample = 17.0,
            priceHalfTola = 85.0,
            priceFullTola = 150.0,
            topNotes = listOf("Spiced Clove", "Black Pepper", "Nutmeg Flower"),
            heartNotes = listOf("Cinnamon Bark", "Henna Leaf", "Sugandh Mantri"),
            baseNotes = listOf("Pure Sandalwood Oil", "Dark Fossil Amber", "Smoked Patchouli"),
            origin = "Kannauj, India",
            distillationMethod = "Multi-stage hydro-distillation of forty rare Himalayan botanical herbs and barks over 15 days",
            longevityHours = 22,
            sillage = SillageLevel.MODERATE,
            description = "A legendary heritage attar of India. Crafted through an intricate 15-day distillation sequence where over forty roots, flowers, spices, and barks are hydro-distilled sequentially into pure sandalwood oil. Deeply warming, spicy, and spiritually grounding.",
            tastingNotes = "Dark ruby-brown oil with warm, spicy, and earthy resinous complexity.",
            applicationRitual = "Rub between wrists and apply to the chest before meditation or winter prayer.",
            occasions = listOf("Chilly Weather", "Spiritual Contemplation", "Traditional Gatherings"),
            season = "Winter",
            rating = 4.86f,
            reviewsCount = 76,
            isBestseller = false,
            isRareReserve = false,
            macerationAge = "Herbal Maceration 18 Months"
        ),
        AttarItem(
            id = "royal-frankincense-hujari",
            name = "Royal Hojari Frankincense",
            arabicName = "اللبان الحوجري الملكي",
            subtitle = "Green Hojari Tears from Dhofar Oman",
            category = AttarCategory.SACRED,
            priceSample = 19.0,
            priceHalfTola = 100.0,
            priceFullTola = 180.0,
            topNotes = listOf("Bright Lime Zest", "Pine Needles", "Eucalyptus Breeze"),
            heartNotes = listOf("Green Sacred Hojari Tears", "Elemi Resin", "White Pepper"),
            baseNotes = listOf("Myrrh", "White Cedar", "Ancient Benzoin"),
            origin = "Dhofar Mountains, Sultanate of Oman",
            distillationMethod = "Low-temperature hydro-distillation of grade-A green tears Boswellia sacra resin",
            longevityHours = 20,
            sillage = SillageLevel.REGAL,
            description = "Sourced directly from the limestone cliffs of Dhofar in Southern Oman. Royal Green Hojari resin has been treasured for millenia as incense of monarchs and prophets. Its scent is crystalline, sparkling with citrus pine before settling into meditative sacred balsamic smoke.",
            tastingNotes = "Crystal clear shimmering oil with refreshing balsamic notes.",
            applicationRitual = "Dot on pulse points before contemplation or dawn prayers for crystal clarity of mind.",
            occasions = listOf("Spiritual Focus", "Morning Prayer", "Quiet Study", "Spring & Fall"),
            season = "All Seasons",
            rating = 4.94f,
            reviewsCount = 103,
            isBestseller = false,
            isRareReserve = true,
            macerationAge = "Pure Resin Extract 2 Years"
        )
    )

    // Educational Articles
    val knowledgeArticles: List<Article> = listOf(
        Article(
            id = "deg-bhapka",
            title = "The 400-Year Deg & Bhapka Heritage",
            subtitle = "Inside the ancient copper stills of Kannauj",
            readTime = "4 min read",
            content = "Unlike modern Western perfumes which dilute fragrant molecules in 80% denatured ethanol, authentic attars are 100% pure botanical and resinous oils hydro-distilled in giant hand-hammered copper cauldrons called Degs. Connected via bamboo pipes (chonga) to water-submerged copper receivers (bhapka) containing pure sandalwood oil, this slow fire technique preserves fragile floral volatile notes that industrial steam would burn."
        ),
        Article(
            id = "attar-vs-perfume",
            title = "Attar vs. Western Eau de Parfum",
            subtitle = "Why pure oil melds uniquely with your body chemistry",
            readTime = "3 min read",
            content = "Alcohol-based perfumes project loudly in the first 15 minutes because alcohol evaporates aggressively into the room. However, they quickly fade and dry out skin. Pure Attar oil contains zero alcohol; it settles directly into your pores, warming gently with your blood circulation. Rather than smelling the same on everyone, an attar evolves organically with your unique body chemistry, lasting 24+ hours."
        ),
        Article(
            id = "application-ritual",
            title = "The Connoisseur's Application Ritual",
            subtitle = "How to apply pure attar with the traditional glass wand",
            readTime = "3 min read",
            content = "1. Never pour directly onto garments, as natural pure oils may leave delicate oil marks.\n2. Gently withdraw the glass applicator wand and draw a line along the inside of your wrist.\n3. Touch wrists together lightly—DO NOT rub violently, as friction crushes delicate top notes.\n4. Tap wrists behind each ear and across the neck pulse points.\n5. Warmth from your arterial pulse activates the gradual release of base and heart notes over the entire day."
        ),
        Article(
            id = "maceration-aging",
            title = "The Art of Maceration & Aging Oud",
            subtitle = "How pure oils evolve and improve over decades",
            readTime = "5 min read",
            content = "Much like vintage fine wine, authentic Agarwood (Dehn Al Oud) and pure Sandalwood oils do not spoil. As time elapses, harsher top notes soften and oxidize, yielding deeper caramel, balsamic, and honeyed facets. A 10-year aged oud possesses a silky roundness that freshly distilled oils simply cannot imitate."
        )
    )

    // Quiz Questions
    val quizQuestions: List<QuizQuestion> = listOf(
        QuizQuestion(
            id = 1,
            question = "What fragrant emotion do you seek to project?",
            subtitle = "Select the aura that best describes your presence",
            options = listOf(
                QuizOption("Majestic & Commanding", "Regal, magnetic presence that leaves an unforgettable royal trail", AttarCategory.OUD),
                QuizOption("Spiritual & Uplifting", "Fresh dewy florals that inspire serenity and peace", AttarCategory.FLORAL),
                QuizOption("Aristocratic & Harmonious", "A sophisticated symphony of rose, woods, and golden amber", AttarCategory.MUKHALLAT),
                QuizOption("Warm & Intimate", "Velvety skin scent that invites warmth and closeness", AttarCategory.AMBER_MUSK),
                QuizOption("Transcendent & Meditative", "Pure sacred resins of ancient temple smoke and citrus pine", AttarCategory.SACRED)
            )
        ),
        QuizQuestion(
            id = 2,
            question = "Which scent profile speaks to your soul?",
            subtitle = "Select your natural olfactory affinity",
            options = listOf(
                QuizOption("Smoky Agarwood & Aged Leather", "Deep resinous woods with honey and earth", AttarCategory.OUD),
                QuizOption("Fresh Damask Rose & Rain-soaked Earth", "Dewy morning florals and green vetiver", AttarCategory.FLORAL),
                QuizOption("Royal Saffron, Oud & Taif Rose", "Complex multilayered regal composition", AttarCategory.MUKHALLAT),
                QuizOption("Sweet Ambergris & Kashmiri Musk", "Cozy, powdery second-skin warmth", AttarCategory.AMBER_MUSK),
                QuizOption("Sacred Frankincense Tears & Myrrh", "Crisp resinous pine and balsamic incense", AttarCategory.SACRED)
            )
        ),
        QuizQuestion(
            id = 3,
            question = "When will you most often wear this attar?",
            subtitle = "Attars evolve dynamically with occasion and climate",
            options = listOf(
                QuizOption("Friday Prayer & Grand Celebrations", "Moments of dignity and celebration", AttarCategory.OUD),
                QuizOption("Daily Office & Quiet Reflection", "Gentle everyday aura that never overwhelms", AttarCategory.FLORAL),
                QuizOption("Evening Galas & Memorable Banquets", "Opulent presence for formal gatherings", AttarCategory.MUKHALLAT),
                QuizOption("Intimate Moments & Cozy Winter Evenings", "Soft whisper close to the skin", AttarCategory.AMBER_MUSK),
                QuizOption("Dawn Prayers & Contemplation", "Clearing the mind for spiritual devotion", AttarCategory.SACRED)
            )
        )
    )

    // Reactive State
    private val _wishlistIds = MutableStateFlow<Set<String>>(setOf("oud-hindi-qadeem", "ruh-gulab-kannauj"))
    val wishlistIds: StateFlow<Set<String>> = _wishlistIds.asStateFlow()

    private val _customBlends = MutableStateFlow<List<CustomBlend>>(listOf(
        CustomBlend(
            id = "blend-default-1",
            name = "Sultan's Velvet Oud",
            baseNote = "Assam Agarwood 12Y",
            baseDrops = 6,
            heartNote = "Kannauj Damask Rose",
            heartDrops = 4,
            topNote = "Royal Kashmiri Saffron",
            topDrops = 2,
            timestamp = System.currentTimeMillis() - 86400000L,
            calculatedSillage = "Regal Radiance",
            harmonyScore = 96
        )
    ))
    val customBlends: StateFlow<List<CustomBlend>> = _customBlends.asStateFlow()

    private val _scentLogs = MutableStateFlow<List<ScentLog>>(listOf(
        ScentLog(
            id = "log-1",
            attarName = "Dehn Al Oud Hindi Qadeem",
            date = "Yesterday, Jumu'ah",
            occasion = "Friday Congregation Prayer",
            longevityHours = 28,
            compliments = 5,
            mood = "Dignified & Spiritually Grounded",
            thoughts = "Applied with glass wand at 11 AM. The honeyed leathery base was still projecting clearly through the evening meal."
        ),
        ScentLog(
            id = "log-2",
            attarName = "Ruh Gulab (Damask Rose)",
            date = "3 days ago",
            occasion = "Family Gathering",
            longevityHours = 18,
            compliments = 4,
            mood = "Joyous & Uplifted",
            thoughts = "Layered with a tiny touch of sandalwood. Heavenly natural rose aura."
        )
    ))
    val scentLogs: StateFlow<List<ScentLog>> = _scentLogs.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(listOf(
        CartItem(
            attarId = "mukhallat-al-sultan",
            attarName = "Mukhallat Al-Sultan",
            arabicName = "مخلط السلطان الفاخر",
            size = TolaSize.SAMPLE,
            unitPrice = 22.0,
            quantity = 1
        )
    ))
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _placedOrdersCount = MutableStateFlow(0)
    val placedOrdersCount: StateFlow<Int> = _placedOrdersCount.asStateFlow()

    fun toggleWishlist(attarId: String) {
        _wishlistIds.update { current ->
            if (current.contains(attarId)) current - attarId else current + attarId
        }
    }

    fun isWishlisted(attarId: String): Boolean = _wishlistIds.value.contains(attarId)

    fun addToCart(attar: AttarItem, size: TolaSize) {
        val price = attar.getPriceForSize(size)
        _cartItems.update { items ->
            val existing = items.find { it.attarId == attar.id && it.size == size }
            if (existing != null) {
                items.map {
                    if (it.attarId == attar.id && it.size == size) it.copy(quantity = it.quantity + 1)
                    else it
                }
            } else {
                items + CartItem(
                    attarId = attar.id,
                    attarName = attar.name,
                    arabicName = attar.arabicName,
                    size = size,
                    unitPrice = price,
                    quantity = 1
                )
            }
        }
    }

    fun updateCartQuantity(attarId: String, size: TolaSize, delta: Int) {
        _cartItems.update { items ->
            items.mapNotNull { item ->
                if (item.attarId == attarId && item.size == size) {
                    val newQty = item.quantity + delta
                    if (newQty > 0) item.copy(quantity = newQty) else null
                } else item
            }
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun recordOrder() {
        _placedOrdersCount.update { it + 1 }
        clearCart()
    }

    fun saveCustomBlend(blend: CustomBlend) {
        _customBlends.update { listOf(blend) + it }
    }

    fun deleteCustomBlend(blendId: String) {
        _customBlends.update { it.filterNot { b -> b.id == blendId } }
    }

    fun addScentLog(log: ScentLog) {
        _scentLogs.update { listOf(log) + it }
    }

    fun deleteScentLog(logId: String) {
        _scentLogs.update { it.filterNot { l -> l.id == logId } }
    }

    fun getAttarById(id: String): AttarItem? = curatedCatalog.find { it.id == id }
}
