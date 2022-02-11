package br.com.moraesit.domain

data class ProductInfo(
    var productId: String? = null,
    var productOptions: MutableList<ProductOption> = mutableListOf()
)