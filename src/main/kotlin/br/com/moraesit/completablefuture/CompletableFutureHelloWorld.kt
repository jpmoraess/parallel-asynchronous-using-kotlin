package br.com.moraesit.completablefuture

import br.com.moraesit.service.HelloWorldService
import br.com.moraesit.util.CommonUtil.Companion.delay
import java.util.concurrent.CompletableFuture

private val hws = HelloWorldService()

fun main() {

    CompletableFuture.supplyAsync { hws.helloWorld() }
        .thenAccept { println(it) }.join()

    println("Done!")

    //delay(2000)
}