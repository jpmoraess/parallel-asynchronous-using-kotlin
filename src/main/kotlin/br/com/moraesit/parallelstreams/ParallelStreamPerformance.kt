package br.com.moraesit.parallelstreams

import br.com.moraesit.util.CommonUtil.Companion.startTimer
import br.com.moraesit.util.CommonUtil.Companion.stopWatchReset
import br.com.moraesit.util.CommonUtil.Companion.timeTaken
import java.util.stream.IntStream
import java.util.stream.Stream

class ParallelStreamPerformance {

    fun sumUsingIntStream(count: Int, isParallel: Boolean): Int {
        startTimer()

        val intStream = IntStream.rangeClosed(0, count)

        if (isParallel) intStream.parallel()

        val sum = intStream.sum()

        timeTaken()
        stopWatchReset()

        return sum
    }

    fun sumUsingList(inputList: List<Int>, isParallel: Boolean): Int {
        startTimer()

        val inputStream = inputList.stream()

        if (isParallel) inputStream.parallel()

        val sum = inputStream.mapToInt(Int::toInt) // unboxing
            .sum()

        timeTaken()
        stopWatchReset()

        return sum
    }

    fun sumUsingIterate(n: Int, isParallel: Boolean): Int {
        startTimer()

        val integerStream = Stream.iterate(0) { i -> i + 1 }

        if (isParallel) integerStream.parallel()

        val sum = integerStream.limit((n + 1).toLong())
            .reduce(0) { x, y -> x + y }

        timeTaken()
        stopWatchReset()

        return sum
    }
}