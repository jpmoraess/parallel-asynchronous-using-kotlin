package br.com.moraesit.service

import br.com.moraesit.parallelstreams.ArrayListSpliteratorExample
import org.junit.jupiter.api.RepeatedTest
import java.util.stream.IntStream
import kotlin.test.assertEquals

class ArrayListSpliteratorExampleTest {

    private val arrayListSpliteratorExample = ArrayListSpliteratorExample()

    @RepeatedTest(5)
    fun multiplyEachValue() {
        //given
        val size = 1000000
        val inputList = generateArrayList(size)

        //when
        val resultList = arrayListSpliteratorExample.multiplyEachValue(inputList, 2, false)

        //then
        assertEquals(size, resultList.size)
    }

    @RepeatedTest(5)
    fun multiplyEachValue_parallel() {
        //given
        val size = 1000000
        val inputList = generateArrayList(size)

        //when
        val resultList = arrayListSpliteratorExample.multiplyEachValue(inputList, 2, true)

        //then
        assertEquals(size, resultList.size)
    }

    private fun generateArrayList(maxNumber: Int): ArrayList<Int> {
        val arrayList = ArrayList<Int>()
        IntStream.rangeClosed(1, maxNumber)
            .boxed()
            .forEach { arrayList.add(it) }
        return arrayList
    }
}