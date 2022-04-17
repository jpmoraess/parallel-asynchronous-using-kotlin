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

        val helloWorld = hello.thenCombine(world) { h, w -> h + w }
            .thenApply { it.uppercase(Locale.getDefault()) }
            .join()

        timeTaken()

        return helloWorld
    }

    fun helloworld_3_async_calls(): String {
        startTimer()

        val hi = CompletableFuture.supplyAsync { hws.hiCompletableFuture() }
        val hello = CompletableFuture.supplyAsync { hws.hello() }
        val world = CompletableFuture.supplyAsync { hws.world() }

        val hiHelloWorld = hi.thenCombine(hello) { h, he -> h + he }
            .thenCombine(world) { hhe, w -> hhe + w }
            .thenApply { it.uppercase(Locale.getDefault()) }
            .join()

        timeTaken()

        return hiHelloWorld
    }

    fun helloworld_3_async_calls_log(): String {
        startTimer()

        val hi = CompletableFuture.supplyAsync { hws.hiCompletableFuture() }
        val hello = CompletableFuture.supplyAsync { hws.hello() }
        val world = CompletableFuture.supplyAsync { hws.world() }

        val hiHelloWorld = hi.thenCombine(hello) { h, he ->
            println("[${Thread.currentThread().name}]: inside hi.thenCombine")
            return@thenCombine h + he
        }
            .thenCombine(world) { hhe, w ->
                println("[${Thread.currentThread().name}]: inside world.thenCombine")
                return@thenCombine hhe + w
            }
            .thenApply {
                println("[${Thread.currentThread().name}]: inside thenApply")
                it.uppercase(Locale.getDefault())
            }
            .join()

        timeTaken()

        return hiHelloWorld
    }

    fun helloWorldThenCompose(): CompletableFuture<String> {
        return CompletableFuture.supplyAsync { hws.hello() }
            .thenCompose { prev -> hws.worldFuture(prev) }
    }

    fun lengthOfString(): CompletableFuture<String> {
        return CompletableFuture.supplyAsync { hws.helloWorld() }
            .thenApply { "${it.length} - $it" }
    }
}