package br.com.moraesit.domain.checkout

data class CheckoutResponse(var checkoutStatus: CheckoutStatus, var errorList: List<CartItem>, var finalPrice: Double)