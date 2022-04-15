package br.com.moraesit.completablefuture

import br.com.moraesit.MockitoHelper
import br.com.moraesit.service.InventoryService
import br.com.moraesit.service.ProductInfoService
import br.com.moraesit.service.ReviewService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class ProductServiceUsingCompletableFutureExceptionTest {

    @Mock
    private val productInfoServiceMock = ProductInfoService()

    @Mock
    private val reviewServiceMock = ReviewService()

    @Mock
    private val inventoryServiceMock = InventoryService()

    @InjectMocks
    lateinit var pscf: ProductServiceUsingCompletableFuture

    @Test
    fun retrieveProductDetailsWithInventory_approach2() {
        // given
        val productId = "ABC123"

        `when`(productInfoServiceMock.retrieveProductInfo(anyString())).thenCallRealMethod()
        `when`(reviewServiceMock.retriveReview(anyString())).thenThrow(RuntimeException("Exception Occured"))
        `when`(inventoryServiceMock.retrieveInventory(MockitoHelper.anyObject())).thenCallRealMethod()

        // when
        val product = pscf.retrieveProductDetailsWithInventory_approach2(productId)

        // then
        assertNotNull(product)
        assertTrue(product.productInfo!!.productOptions.size > 0)
        product.productInfo!!.productOptions.forEach { productOption ->
            assertNotNull(productOption.inventory)
            assertEquals(2, productOption.inventory!!.count)
        }
        assertNotNull(product.review)
        assertEquals(0, product.review!!.noOfReview)
        assertEquals(0.0, product.review!!.overallRating)
    }

    @Test
    fun retrieveProductDetailsWithInventory_productInfoServiceError() {
        // given
        val productId = "ABC123"

        `when`(productInfoServiceMock.retrieveProductInfo(anyString())).thenThrow(RuntimeException("Exception Occured"))
        `when`(reviewServiceMock.retriveReview(anyString())).thenCallRealMethod()
        //`when`(inventoryServiceMock.retrieveInventory(MockitoHelper.anyObject())).thenCallRealMethod()

        // then
        Assertions.assertThrows(RuntimeException::class.java) {
            pscf.retrieveProductDetailsWithInventory_approach2(productId)
        }
    }
}