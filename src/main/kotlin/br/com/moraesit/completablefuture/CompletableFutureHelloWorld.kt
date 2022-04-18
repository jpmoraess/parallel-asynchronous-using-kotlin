package br.com.moraesit.completablefuture

import br.com.moraesit.service.HelloWorldService
import br.com.moraesit.util.CommonUtil.Companion.delay
import br.com.moraesit.util.CommonUtil.Companion.startTimer
import br.com.moraesit.util.CommonUtil.Companion.timeTaken
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

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

    fun helloworld_3_async_calls_log_async(): String {
        startTimer()

        val hi = CompletableFuture.supplyAsync { hws.hiCompletableFuture() }
        val hello = CompletableFuture.supplyAsync { hws.hello() }
        val world = CompletableFuture.supplyAsync { hws.world() }

        val hiHelloWorld = hi.thenCombineAsync(hello) { h, he ->
            println("[${Thread.currentThread().name}]: inside hi.thenCombineAsync")
            return@thenCombineAsync h + he
        }
            .thenCombineAsync(world) { hhe, w ->
                println("[${Thread.currentThread().name}]: inside world.thenCombineAsync")
                return@thenCombineAsync hhe + w
            }
            .thenApplyAsync {
                println("[${Thread.currentThread().name}]: inside thenApplyAsync")
                return@thenApplyAsync it.uppercase(Locale.getDefault())
            }
            .join()

        timeTaken()

        return hiHelloWorld
    }

    fun helloworld_3_async_calls_custom_threadpool(): String {
        startTimer()

        val executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

        val hi = CompletableFuture.supplyAsync({ hws.hiCompletableFuture() }, executorService)
        val hello = CompletableFuture.supplyAsync({ hws.hello() }, executorService)
        val world = CompletableFuture.supplyAsync({ hws.world() }, executorService)

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

    fun helloworld_3_async_calls_custom_threadpool_async(): String {
        startTimer()

        val executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

        val hi = CompletableFuture.supplyAsync({ hws.hiCompletableFuture() }, executorService)
        val hello = CompletableFuture.supplyAsync({ hws.hello() }, executorService)
        val world = CompletableFuture.supplyAsync({ hws.world() }, executorService)

        val hiHelloWorld = hi.thenCombineAsync(hello, { h, he ->
            println("[${Thread.currentThread().name}]: inside hi.thenCombine")
            return@thenCombineAsync h + he
        }, executorService)
            .thenCombineAsync(world, { hhe, w ->
                println("[${Thread.currentThread().name}]: inside world.thenCombine")
                return@thenCombineAsync hhe + w
            }, executorService)
            .thenApplyAsync({
                println("[${Thread.currentThread().name}]: inside thenApply")
                return@thenApplyAsync it.uppercase(Locale.getDefault())
            }, executorService)
            .join()

        timeTaken()

        return hiHelloWorld
    }

    fun helloWorldThenCompose(): CompletableFuture<String> {
        return CompletableFuture.supplyAsync { hws.hello() }
            .thenCompose { prev -> hws.worldFuture(prev) }
    }

    fun anyOf(): String? {
        // db call
        val dbCall = CompletableFuture.supplyAsync {
            delay(2050)
            println("response from dbCall")
            return@supplyAsync "hello world"
        }

        // rest call
        val restCall = CompletableFuture.supplyAsync {
            delay(2005)
            println("response from restCall")
            return@supplyAsync "hello world"
        }

        // soap call
        val soapCall = CompletableFuture.supplyAsync {
            delay(2300)
            println("response from soapCall")
            return@supplyAsync "hello world"
        }

        val cfList = listOf(dbCall, restCall, soapCall)

        val anyOf = CompletableFuture.anyOf(*cfList.toTypedArray())

        val result = anyOf.thenApply { v ->
            if (v is String) return@thenApply v else return@thenApply null
        }.join()

        return result
    }

    fun lengthOfString(): CompletableFuture<String> {
        return CompletableFuture.supplyAsync { hws.helloWorld() }
            .thenApply { "${it.length} - $it" }
    }
}