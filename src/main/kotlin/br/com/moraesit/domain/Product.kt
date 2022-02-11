package br.com.moraesit.domain

data class Product(
    var productId: String? = null,
    var productInfo: ProductInfo? = null,
    var review: Review? = null
)