package br.com.moraesit.domain.checkout

data class Cart(var cartId: Int, var carItemList: List<CartItem>)