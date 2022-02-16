package br.com.moraesit.service

import br.com.moraesit.domain.checkout.Cart
import br.com.moraesit.domain.checkout.CheckoutResponse
import br.com.moraesit.domain.checkout.CheckoutStatus
import br.com.moraesit.util.CommonUtil
import java.util.stream.Collectors

class CheckoutService(
    private val priceValidatorService: PriceValidatorService
) {

    fun checkout(cart: Cart): CheckoutResponse {
        CommonUtil.startTimer()

        val priceValidationList = cart.carItemList
            //.stream()
            .parallelStream()
            .map {
                val isPriceInvalid = priceValidatorService.isCartItemInvalid(it)
                it.isExpired = isPriceInvalid
                it
            }
            .filter { it.isExpired }
            .collect(Collectors.toList())

        if (priceValidationList.size > 0)
            return CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList, 0.0)

        //val finalPrice = calculateFinalPrice(cart)
        val finalPrice = calculateFinalPriceReduce(cart)
        println("Checkout Complete and the final price is: $finalPrice")

        CommonUtil.timeTaken()
        CommonUtil.stopWatchReset()

        return CheckoutResponse(CheckoutStatus.SUCCESS, emptyList(), finalPrice)
    }

    fun calculateFinalPrice(cart: Cart): Double {
//        return cart.carItemList.parallelStream()
//            .map { it.price * it.quantity }
//            .collect(summingDouble(Double::toDouble))

        return cart.carItemList.parallelStream()
            .map { it.price * it.quantity }
            .mapToDouble(Double::toDouble)
            .sum()
    }

    fun calculateFinalPriceReduce(cart: Cart): Double {
        return cart.carItemList.parallelStream()
            .map { it.price * it.quantity }
            .reduce(0.0) { x, y -> x + y }
    }
}