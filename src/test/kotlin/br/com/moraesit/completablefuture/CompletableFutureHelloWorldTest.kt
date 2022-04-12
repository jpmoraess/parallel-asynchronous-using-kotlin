package br.com.moraesit.completablefuture

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CompletableFutureHelloWorldTest {

    private val cfhw = CompletableFutureHelloWorld()

    @Test
    fun helloWorld() {
        val completableFuture = cfhw.helloWorld()

        completableFuture
            .thenAccept {
                assertEquals("HELLO WORLD", it)
            }.join()
    }

    @Test
    fun helloworld_multiple_async_calls() {
        val helloWorld = cfhw.helloworld_multiple_async_calls()

        assertEquals("HELLO WORLD!", helloWorld)
    }

    @Test
    fun helloworld_3_async_calls() {
        val hiHelloWorld = cfhw.helloworld_3_async_calls()

        assertEquals("HI COMPLETABLEFUTURE HELLO WORLD!", hiHelloWorld)
    }

    @Test
    fun lengthOfString() {
        val completableFuture = cfhw.lengthOfString()

        completableFuture
            .thenAccept {
                assertEquals("11 - hello world", it)
            }.join()
    }
}