package br.com.moraesit.completablefuture

import br.com.moraesit.domain.Product
import br.com.moraesit.service.ProductInfoService
import br.com.moraesit.service.ReviewService
import br.com.moraesit.util.CommonUtil
import java.util.concurrent.CompletableFuture

class ProductServiceUsingCompletableFuture(
    private val reviewService: ReviewService,
    private val productInfoService: ProductInfoService,
) {

    fun retrieveProductDetails(productId: String): Product {
        CommonUtil.startTimer()

        val cfProductInfo = CompletableFuture.supplyAsync { productInfoService.retrieveProductInfo(productId) }
        val cfReview = CompletableFuture.supplyAsync { reviewService.retriveReview(productId) }

        val product = cfProductInfo.thenCombine(cfReview) { productInfo, review ->
            Product(productId, productInfo, review)
        }.join() // block the thread

        CommonUtil.timeTaken()
        return product
    }

    fun retrieveProductDetails_approach2(productId: String): CompletableFuture<Product> {
        val cfProductInfo = CompletableFuture.supplyAsync { productInfoService.retrieveProductInfo(productId) }
        val cfReview = CompletableFuture.supplyAsync { reviewService.retriveReview(productId) }

        return cfProductInfo.thenCombine(cfReview) { productInfo, review ->
            Product(productId, productInfo, review)
        }
    }
}

fun main(args: Array<String>) {
    val productInfoService = ProductInfoService()
    val reviewService = ReviewService()
    val productService = ProductServiceUsingCompletableFuture(reviewService, productInfoService)
    val productId = "ABC123"
    val product = productService.retrieveProductDetails(productId)
    println(product)
}