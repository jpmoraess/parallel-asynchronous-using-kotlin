package br.com.moraesit.domain.checkout

import java.math.BigDecimal

data class CartItem(var itemId: Int, var itemName: String, var price: Double, var quantity: Int, var isExpired: Boolean)