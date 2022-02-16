package br.com.moraesit.service

import br.com.moraesit.domain.checkout.Cart
import br.com.moraesit.domain.checkout.CartItem
import br.com.moraesit.domain.checkout.CheckoutStatus
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.concurrent.ForkJoinPool
import kotlin.random.Random
import kotlin.test.assertEquals

class CheckoutServiceTest {
    private val checkoutService = CheckoutService(PriceValidatorService())

    @Test
    fun noOfCores() {
        println("no of cores: ${Runtime.getRuntime().availableProcessors()}")
    }

    @Test
    fun parallelism() {
        println("parallelism: ${ForkJoinPool.getCommonPoolParallelism()}")
    }

    @Test
    fun checkout_6_items() {
        //given
        val cart = cartGenerator(6)

        //when
        val checkoutResponse = checkoutService.checkout(cart)

        //then
        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.checkoutStatus)
        assertTrue(checkoutResponse.finalPrice > 0.0)
    }

    @Test
    fun checkout_13_items() {
        //given
        val cart = cartGenerator(13)

        //when
        val checkoutResponse = checkoutService.checkout(cart)

        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.checkoutStatus)

    }

    @Test
    fun modify_parallelism() {
        //given
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100")

        val cart = cartGenerator(100)

        //when
        val checkoutResponse = checkoutService.checkout(cart)

        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.checkoutStatus)

    }

    private fun cartGenerator(noOfItems: Int): Cart {
        val cart = Cart(1, mutableListOf())
        IntRange(1, noOfItems).forEachIndexed { index, _ ->
            val itemName = "CartItem-$index"
            val cartItem = CartItem(index, itemName, Random.nextDouble(20.0, 200.0), index + 1, false)
            cart.carItemList.add(cartItem)
        }
        return cart
    }
}
