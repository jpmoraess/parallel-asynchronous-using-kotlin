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
            .stream()
            .map {
                val isPriceInvalid = priceValidatorService.isCartItemInvalid(it)
                it.isExpired = isPriceInvalid
                it
            }
            .filter { it.isExpired }
            .collect(Collectors.toList())

        if (priceValidationList.size > 0)
            return CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList)

        CommonUtil.timeTaken()

        return CheckoutResponse(CheckoutStatus.SUCCESS, emptyList())
    }
}