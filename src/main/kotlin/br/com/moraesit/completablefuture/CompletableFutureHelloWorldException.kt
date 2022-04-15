package br.com.moraesit.completablefuture

import br.com.moraesit.service.HelloWorldService
import br.com.moraesit.util.CommonUtil
import java.util.*
import java.util.concurrent.CompletableFuture

class CompletableFutureHelloWorldException {

    private val hws = HelloWorldService()

    fun helloworld_3_async_calls_handle(): String {
        CommonUtil.startTimer()

        val hi = CompletableFuture.supplyAsync { hws.hiCompletableFuture() }
        val hello = CompletableFuture.supplyAsync { hws.hello() }
        val world = CompletableFuture.supplyAsync { hws.world() }

        val hiHelloWorld =
            hi.handle { _, e ->
                System.err.println("Exception is: $e")
                return@handle ""
            }
                .thenCombine(hello) { h, he -> h + he }
                .handle { _, e ->
                    System.err.println("Exception 2 is: $e")
                    return@handle ""
                }
                .thenCombine(world) { hhe, w -> hhe + w }
                .thenApply { it.uppercase(Locale.getDefault()) }
                .join()

        CommonUtil.timeTaken()

        return hiHelloWorld
    }
}