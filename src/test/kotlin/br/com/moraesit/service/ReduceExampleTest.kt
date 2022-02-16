package br.com.moraesit.service

import br.com.moraesit.parallelstreams.ReduceExample
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ReduceExampleTest {

    private val reduceExample = ReduceExample()

    @Test
    fun reduceSumParallelStream() {
        val inputList = listOf(1, 2, 3, 4, 5, 6, 7, 8)

        val result = reduceExample.reduceSumParallelStream(inputList)

        assertEquals(36, result)
    }

    @Test
    fun reduceSumParallelStream_emptyList() {
        val inputList = emptyList<Int>()

        val result = reduceExample.reduceSumParallelStream(inputList)

        assertEquals(0, result)
    }

    @Test
    fun reduceMultiplyParallelStream() {
        val inputList = listOf(1, 2, 3, 4, 5, 6, 7, 8)

        val result = reduceExample.reduceMultiplyParallelStream(inputList)

        assertEquals(40320, result)
    }

    @Test
    fun reduceMultiplyParallelStream_emptyList() {
        val inputList = emptyList<Int>()

        val result = reduceExample.reduceMultiplyParallelStream(inputList)

        assertEquals(1, result)
    }
}