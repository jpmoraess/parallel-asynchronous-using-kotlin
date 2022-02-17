package br.com.moraesit.completablefuture

import br.com.moraesit.service.HelloWorldService
import java.util.*
import java.util.concurrent.CompletableFuture

private val hws = HelloWorldService()

fun main() {

    CompletableFuture.supplyAsync { hws.helloWorld() }
        .thenApply { it.uppercase(Locale.getDefault()) }
        .thenAccept { println(it) }.join()

    println("Done!")

    //delay(2000)
}