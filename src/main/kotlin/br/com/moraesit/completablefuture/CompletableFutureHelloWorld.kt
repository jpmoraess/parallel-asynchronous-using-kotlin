package br.com.moraesit.completablefuture

import br.com.moraesit.service.HelloWorldService
import java.util.*
import java.util.concurrent.CompletableFuture

class CompletableFutureHelloWorld {
    private val hws = HelloWorldService()

    fun helloWorld(): CompletableFuture<String> {
        return CompletableFuture.supplyAsync { hws.helloWorld() }
            .thenApply { it.uppercase(Locale.getDefault()) }
    }
}