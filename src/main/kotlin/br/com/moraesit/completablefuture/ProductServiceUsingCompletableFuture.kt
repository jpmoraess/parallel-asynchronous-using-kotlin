package br.com.moraesit.completablefuture

import br.com.moraesit.domain.Product
import br.com.moraesit.domain.ProductInfo
import br.com.moraesit.domain.ProductOption
import br.com.moraesit.domain.Review
import br.com.moraesit.service.InventoryService
import br.com.moraesit.service.ProductInfoService
import br.com.moraesit.service.ReviewService
import br.com.moraesit.util.CommonUtil
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

class ProductServiceUsingCompletableFuture(
    private val reviewService: ReviewService,
    private val productInfoService: ProductInfoService,
    private val inventoryService: InventoryService,
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

    fun retrieveProductDetailsWithInventory(productId: String): Product {
        CommonUtil.startTimer()

        val cfProductInfo = CompletableFuture.supplyAsync { productInfoService.retrieveProductInfo(productId) }
            .thenApply { productInfo ->
                productInfo.productOptions = updateInventory(productInfo)
                return@thenApply productInfo
            }
        val cfReview = CompletableFuture.supplyAsync { reviewService.retriveReview(productId) }

        val product = cfProductInfo.thenCombine(cfReview) { productInfo, review ->
            Product(productId, productInfo, review)
        }.join() // block the thread

        CommonUtil.timeTaken()
        return product
    }

    fun retrieveProductDetailsWithInventory_approach2(productId: String): Product {
        CommonUtil.startTimer()

        val cfProductInfo = CompletableFuture.supplyAsync { productInfoService.retrieveProductInfo(productId) }
            .thenApply { productInfo ->
                productInfo.productOptions = updateInventory_approach2(productInfo)
                return@thenApply productInfo
            }
        val cfReview = CompletableFuture.supplyAsync { reviewService.retriveReview(productId) }
            .exceptionally { e ->
                System.err.println("Handled the Exception in ReviewService: ${e.message}")
                return@exceptionally Review(noOfReview = 0, overallRating = 0.0)
            }

        val product = cfProductInfo.thenCombine(cfReview) { productInfo, review ->
            Product(productId, productInfo, review)
        }.join() // block the thread

        CommonUtil.timeTaken()
        return product
    }

    private fun updateInventory(productInfo: ProductInfo): MutableList<ProductOption> {
        return productInfo.productOptions.stream().map { productOption ->
            val inventory = inventoryService.retrieveInventory(productOption)
            productOption.inventory = inventory
            return@map productOption
        }
            .collect(Collectors.toList())
    }

    private fun updateInventory_approach2(productInfo: ProductInfo): MutableList<ProductOption> {
        return productInfo.productOptions.stream().map { productOption ->
            return@map CompletableFuture.supplyAsync { inventoryService.retrieveInventory(productOption) }
                .thenApply { inventory ->
                    productOption.inventory = inventory
                    return@thenApply productOption
                }
        }
            .collect(Collectors.toList())
            .stream().map { it.join() }
            .collect(Collectors.toList())
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
    val inventoryService = InventoryService()
    val productService = ProductServiceUsingCompletableFuture(reviewService, productInfoService, inventoryService)
    val productId = "ABC123"
    val product = productService.retrieveProductDetailsWithInventory_approach2(productId)
    println(product)
}