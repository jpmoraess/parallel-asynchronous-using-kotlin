package br.com.moraesit.service

import br.com.moraesit.util.CommonUtil.Companion.delay
import java.util.concurrent.CompletableFuture

class HelloWorldService {

    fun helloWorld(): String {
        delay(1000)
        println("inside helloWorld")
        return "hello world"
    }

    fun hello(): String {
        delay(1000)
        println("inside hello")
        return "hello "
    }

    fun world(): String {
        delay(1000)
        println("inside world")
        return "world!"
    }

    fun hiCompletableFuture(): String {
        delay(1000)
        return "Hi CompletableFuture "
    }

    fun worldFuture(input: String): CompletableFuture<String> {
        return CompletableFuture.supplyAsync {
            delay(1000)
            return@supplyAsync "${input}world!"
        }
    }
}