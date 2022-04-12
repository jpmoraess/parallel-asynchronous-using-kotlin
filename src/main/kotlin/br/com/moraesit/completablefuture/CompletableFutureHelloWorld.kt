package br.com.moraesit.completablefuture

import br.com.moraesit.service.HelloWorldService
import br.com.moraesit.util.CommonUtil.Companion.startTimer
import br.com.moraesit.util.CommonUtil.Companion.timeTaken
import java.util.*
import java.util.concurrent.CompletableFuture

class CompletableFutureHelloWorld {
    private val hws = HelloWorldService()

    fun helloWorld(): CompletableFuture<String> {
        return CompletableFuture.supplyAsync { hws.helloWorld() }
            .thenApply { it.uppercase(Locale.getDefault()) }
    }

    fun helloworld_multiple_async_calls(): String {
        startTimer()

        val hello = CompletableFuture.supplyAsync { hws.hello() }
        val world = CompletableFuture.supplyAsync { hws.world() }

        val helloWorld =  hello.thenCombine(world) { h, w -> h + w }
            .thenApply { it.uppercase(Locale.getDefault()) }
            .join()

        timeTaken()

        return helloWorld
    }

    fun lengthOfString(): CompletableFuture<String> {
        return CompletableFuture.supplyAsync { hws.helloWorld() }
            .thenApply { "${it.length} - $it" }
    }
}