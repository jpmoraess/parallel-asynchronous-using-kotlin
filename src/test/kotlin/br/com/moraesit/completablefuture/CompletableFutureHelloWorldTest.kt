package br.com.moraesit.completablefuture

import br.com.moraesit.util.CommonUtil.Companion.startTimer
import br.com.moraesit.util.CommonUtil.Companion.timeTaken
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
    fun helloworld_3_async_calls_log() {
        val hiHelloWorld = cfhw.helloworld_3_async_calls_log()

        assertEquals("HI COMPLETABLEFUTURE HELLO WORLD!", hiHelloWorld)
    }

    @Test
    fun helloworld_3_async_calls_log_async() {
        val hiHelloWorld = cfhw.helloworld_3_async_calls_log_async()

        assertEquals("HI COMPLETABLEFUTURE HELLO WORLD!", hiHelloWorld)
    }

    @Test
    fun helloworld_3_async_custom_threadpool() {
        val hiHelloWorld = cfhw.helloworld_3_async_calls_custom_threadpool()

        assertEquals("HI COMPLETABLEFUTURE HELLO WORLD!", hiHelloWorld)
    }

    @Test
    fun helloworld_3_async_custom_threadpool_async() {
        val hiHelloWorld = cfhw.helloworld_3_async_calls_custom_threadpool_async()

        assertEquals("HI COMPLETABLEFUTURE HELLO WORLD!", hiHelloWorld)
    }

    @Test
    fun helloWorldThenCompose() {
        startTimer()

        val completableFuture = cfhw.helloWorldThenCompose()

        completableFuture
            .thenAccept {
                assertEquals("hello world!", it)
            }.join()

        timeTaken()
    }

    @Test
    fun anyOf() {
        val helloWorld = cfhw.anyOf()

        assertEquals("hello world", helloWorld)
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