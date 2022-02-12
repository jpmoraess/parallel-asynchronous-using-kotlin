package br.com.moraesit.executor

import br.com.moraesit.domain.Product
import br.com.moraesit.domain.ProductInfo
import br.com.moraesit.domain.Review
import br.com.moraesit.service.ProductInfoService
import br.com.moraesit.service.ReviewService
import br.com.moraesit.util.CommonUtil
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private val executorService: ExecutorService =
    Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

class ProductServiceUsingExecutor(
    private val reviewService: ReviewService,
    private val productInfoService: ProductInfoService,
) {

    fun retrieveProductDetails(productId: String): Product {

        CommonUtil.startTimer()

        val productInfoFuture =
            executorService.submit<ProductInfo> { productInfoService.retrieveProductInfo(productId) }

        val reviewFuture = executorService.submit<Review> { reviewService.retriveReview(productId) }

        //val productInfo = productInfoFuture.get()
        val productInfo = productInfoFuture.get(2, TimeUnit.SECONDS)
        //val review = reviewFuture.get()
        val review = reviewFuture.get(2, TimeUnit.SECONDS)

        CommonUtil.timeTaken()
        return Product(productId, productInfo, review)
    }
}

fun main(args: Array<String>) {
    val productInfoService = ProductInfoService()
    val reviewService = ReviewService()
    val productService = ProductServiceUsingExecutor(reviewService, productInfoService)
    val productId = "ABC123"
    val product = productService.retrieveProductDetails(productId)
    println(product)
    executorService.shutdown()
}