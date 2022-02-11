package br.com.moraesit.service

import br.com.moraesit.domain.ProductInfo
import br.com.moraesit.domain.ProductOption
import br.com.moraesit.util.CommonUtil.Companion.delay
import java.math.BigDecimal

class ProductInfoService {

    fun retrieveProductInfo(productId: String): ProductInfo {
        delay(1000)
        val productOptions = listOf(
            ProductOption().apply {
                productOptionId = 1
                size = "64GB"
                color = "Black"
                price = BigDecimal(3000.00)
            },
            ProductOption().apply {
                productOptionId = 2
                size = "128GB"
                color = "Black"
                price = BigDecimal(3000.00)
            })
        return ProductInfo().apply {
            this.productId = productId
            this.productOptions = productOptions.toMutableList()
        }
    }
}