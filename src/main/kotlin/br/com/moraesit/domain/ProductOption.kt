package br.com.moraesit.domain

import java.math.BigDecimal

data class ProductOption(
    var productOptionId: Int? = null,
    var size: String? = null,
    var color: String? = null,
    var price: BigDecimal? = null,
    var inventory: Inventory? = null
)