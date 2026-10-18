package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.AttarCategory
import com.example.data.model.CustomBlend
import com.example.data.model.TolaSize
import com.example.data.repository.AttarRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Attar Atelier", appName)
    }

    @Test
    fun `curated catalog contains authentic attars with notes pyramid`() {
        val catalog = AttarRepository.curatedCatalog
        assertTrue("Catalog should contain items", catalog.isNotEmpty())

        val hindiOud = catalog.find { it.id == "oud-hindi-qadeem" }
        assertNotNull("Hindi Oud should exist", hindiOud)
        assertEquals(AttarCategory.OUD, hindiOud?.category)
        assertTrue(hindiOud?.baseNotes?.isNotEmpty() == true)
        assertTrue(hindiOud?.longevityHours ?: 0 >= 24)
    }

    @Test
    fun `cart operations and tola size pricing`() {
        AttarRepository.clearCart()
        val rose = AttarRepository.getAttarById("ruh-gulab-kannauj")
        assertNotNull(rose)

        AttarRepository.addToCart(rose!!, TolaSize.SAMPLE)
        assertEquals(1, AttarRepository.cartItems.value.size)
        assertEquals(rose.priceSample, AttarRepository.cartItems.value[0].unitPrice, 0.01)

        AttarRepository.updateCartQuantity(rose.id, TolaSize.SAMPLE, 1)
        assertEquals(2, AttarRepository.cartItems.value[0].quantity)

        AttarRepository.clearCart()
        assertTrue(AttarRepository.cartItems.value.isEmpty())
    }

    @Test
    fun `custom blend compounding`() {
        val blend = CustomBlend(
            id = "test-blend-1",
            name = "Royal Damask Oud",
            baseNote = "Assam Agarwood 12Y",
            baseDrops = 6,
            heartNote = "Kannauj Damask Rose",
            heartDrops = 4,
            topNote = "Bergamot",
            topDrops = 2,
            timestamp = System.currentTimeMillis(),
            calculatedSillage = "Regal Radiance",
            harmonyScore = 95
        )
        AttarRepository.saveCustomBlend(blend)
        assertTrue(AttarRepository.customBlends.value.any { it.id == "test-blend-1" })

        AttarRepository.deleteCustomBlend("test-blend-1")
    }
}
