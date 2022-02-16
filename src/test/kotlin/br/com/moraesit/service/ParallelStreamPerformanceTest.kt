package br.com.moraesit.service

import br.com.moraesit.parallelstreams.ParallelStreamPerformance
import kotlin.test.Test
import kotlin.test.assertEquals

class ParallelStreamPerformanceTest {

    private val parallelStreamPerformance = ParallelStreamPerformance()

    @Test
    fun sumUsingIntStream() {

        val sum = parallelStreamPerformance.sumUsingIntStream(1000000, false)
        println("sum: $sum")

        assertEquals(1784293664, sum)
    }

    @Test
    fun sumUsingIntStreamParallel() {

        val sum = parallelStreamPerformance.sumUsingIntStream(1000000, true)
        println("sum: $sum")

        assertEquals(1784293664, sum)
    }

    @Test
    fun sumUsingIterate() {

        val sum = parallelStreamPerformance.sumUsingIterate(1000000, false)
        println("sum: $sum")

        assertEquals(1784293664, sum)
    }

    @Test
    fun sumUsingIterateParallel() {

        val sum = parallelStreamPerformance.sumUsingIterate(1000000, true)
        println("sum: $sum")

        assertEquals(1784293664, sum)
    }
}