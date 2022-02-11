package br.com.moraesit.service

import br.com.moraesit.domain.Product
import br.com.moraesit.util.CommonUtil

class ProductService(
    private val reviewService: ReviewService,
    private val productInfoService: ProductInfoService,
) {

    fun retrieveProductDetails(productId: String): Product {
        CommonUtil.startTimer()

        val productInfo = productInfoService.retrieveProductInfo(productId) // blocking call
        val review = reviewService.retriveReview(productId) // blocking call

        CommonUtil.timeTaken()
        return Product(productId, productInfo, review)
    }
}

fun main(args: Array<String>) {
    val productInfoService = ProductInfoService()
    val reviewService = ReviewService()
    val productService = ProductService(reviewService, productInfoService)
    val productId = "ABC123"
    val product = productService.retrieveProductDetails(productId)
    println(product)
}