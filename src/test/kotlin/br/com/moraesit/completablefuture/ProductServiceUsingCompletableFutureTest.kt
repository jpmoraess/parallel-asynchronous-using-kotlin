package br.com.moraesit.completablefuture

import br.com.moraesit.service.ProductInfoService
import br.com.moraesit.service.ReviewService
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ProductServiceUsingCompletableFutureTest {

    private val reviewService = ReviewService()
    private val productInfoService = ProductInfoService()
    private val service = ProductServiceUsingCompletableFuture(reviewService, productInfoService)

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
}