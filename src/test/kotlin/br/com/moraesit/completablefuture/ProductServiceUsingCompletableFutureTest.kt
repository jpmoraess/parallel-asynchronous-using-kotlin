package br.com.moraesit.completablefuture

import br.com.moraesit.service.InventoryService
import br.com.moraesit.service.ProductInfoService
import br.com.moraesit.service.ReviewService
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ProductServiceUsingCompletableFutureTest {

    private val reviewService = ReviewService()
    private val productInfoService = ProductInfoService()
    private val inventoryService = InventoryService()
    private val service = ProductServiceUsingCompletableFuture(reviewService, productInfoService, inventoryService)

    @Test
    fun retrieveProductDetails() {
        // given
        val productId = "ABC123"

        // when
        val product = service.retrieveProductDetails(productId)

        // then
        assertNotNull(product)
        assertTrue(product.productInfo!!.productOptions.size > 0)
        assertNotNull(product.review)
    }

    @Test
    fun retrieveProductDetails_approach2() {
        // given
        val productId = "ABC123"

        // when
        val productCompletableFuture = service.retrieveProductDetails_approach2(productId)

        // then
        productCompletableFuture.thenAccept {
            assertNotNull(it)
            assertTrue(it.productInfo!!.productOptions.size > 0)
            assertNotNull(it.review)
        }.join()
    }

    @Test
    fun retrieveProductDetailsWithInventory() {
        // given
        val productId = "ABC123"

        // when
        val product = service.retrieveProductDetailsWithInventory(productId)

        // then
        assertNotNull(product)
        assertTrue(product.productInfo!!.productOptions.size > 0)
        product.productInfo!!.productOptions.forEach { productOption ->
            assertNotNull(productOption.inventory)
            assertEquals(productOption.inventory!!.count, 2)
        }
        assertNotNull(product.review)
    }

    @Test
    fun retrieveProductDetailsWithInventory_approach2() {
        // given
        val productId = "ABC123"

        // when
        val product = service.retrieveProductDetailsWithInventory_approach2(productId)

        // then
        assertNotNull(product)
        assertTrue(product.productInfo!!.productOptions.size > 0)
        product.productInfo!!.productOptions.forEach { productOption ->
            assertNotNull(productOption.inventory)
            assertEquals(productOption.inventory!!.count, 2)
        }
        assertNotNull(product.review)
    }
}