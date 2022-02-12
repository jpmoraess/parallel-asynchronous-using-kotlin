package br.com.moraesit.domain.checkout

data class CartItem(var itemId: Int, var itemName: String, var rate: Double, var quantity: Int, var isExpired: Boolean)