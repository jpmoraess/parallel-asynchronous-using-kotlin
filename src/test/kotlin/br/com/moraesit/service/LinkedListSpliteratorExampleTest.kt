package br.com.moraesit.service

import br.com.moraesit.parallelstreams.LinkedListSpliteratorExample
import org.junit.jupiter.api.RepeatedTest
import java.util.*
import java.util.stream.IntStream
import kotlin.test.assertEquals

class LinkedListSpliteratorExampleTest {
    private val linkedListSpliteratorExample = LinkedListSpliteratorExample()

    @RepeatedTest(5)
    fun multiplyEachValue() {
        //given
        val size = 1000000
        val inputList = generateLinkedList(size)

        //when
        val resultList = linkedListSpliteratorExample.multiplyEachValue(inputList, 2, false)

        //then
        assertEquals(size, resultList.size)
    }

    @RepeatedTest(5)
    fun multiplyEachValue_parallel() {
        //given
        val size = 1000000
        val inputList = generateLinkedList(size)

        //when
        val resultList = linkedListSpliteratorExample.multiplyEachValue(inputList, 2, true)

        //then
        assertEquals(size, resultList.size)
    }

    private fun generateLinkedList(maxNumber: Int): LinkedList<Int> {
        val linkedList = LinkedList<Int>()
        IntStream.rangeClosed(1, maxNumber)
            .boxed()
            .forEach { linkedList.add(it) }
        return linkedList
    }
}