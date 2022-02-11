package br.com.moraesit.thread

import br.com.moraesit.domain.Product
import br.com.moraesit.domain.ProductInfo
import br.com.moraesit.domain.Review
import br.com.moraesit.service.ProductInfoService
import br.com.moraesit.service.ReviewService
import br.com.moraesit.util.CommonUtil

class ProductServiceUsingThread(
    private val reviewService: ReviewService,
    private val productInfoService: ProductInfoService,
) {

    fun retrieveProductDetails(productId: String): Product {
        CommonUtil.startTimer()

        val productInfoRunnable = ProductInfoRunnable(productId)
        val productInfoThread = Thread(productInfoRunnable)

        val reviewRunnable = ReviewRunnable(productId)
        val reviewThread = Thread(reviewRunnable)

        productInfoThread.start()
        reviewThread.start()

        productInfoThread.join()
        reviewThread.join()

        val productInfo = productInfoRunnable.productInfo
        val review = reviewRunnable.review

        CommonUtil.timeTaken()
        return Product(productId, productInfo, review)
    }

    private inner class ProductInfoRunnable(productId: String) : Runnable {
        lateinit var productInfo: ProductInfo
        var productId: String

        init {
            this.productId = productId
        }

        override fun run() {
            productInfo = productInfoService.retrieveProductInfo(productId)
        }
    }

    private inner class ReviewRunnable(productId: String) : Runnable {
        lateinit var review: Review
        var productId: String

        init {
            this.productId = productId
        }

        override fun run() {
            review = reviewService.retriveReview(productId)
        }
    }
}

fun main(args: Array<String>) {
    val productInfoService = ProductInfoService()
    val reviewService = ReviewService()
    val productService = ProductServiceUsingThread(reviewService, productInfoService)
    val productId = "ABC123"
    val product = productService.retrieveProductDetails(productId)
    println(product)
}