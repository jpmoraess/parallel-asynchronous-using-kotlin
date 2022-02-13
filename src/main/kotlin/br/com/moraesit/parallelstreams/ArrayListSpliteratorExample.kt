package br.com.moraesit.parallelstreams

import br.com.moraesit.util.CommonUtil.Companion.startTimer
import br.com.moraesit.util.CommonUtil.Companion.stopWatchReset
import br.com.moraesit.util.CommonUtil.Companion.timeTaken
import java.util.stream.Collectors

class ArrayListSpliteratorExample {

    fun multiplyEachValue(inputList: ArrayList<Int>, multiplyValue: Int, isParallel: Boolean): List<Int> {
        startTimer()
        val integerStream = inputList.stream() // sequential

        if (isParallel) integerStream.parallel() // parallel

        val resultList = integerStream.map { it * multiplyValue }
            .collect(Collectors.toList())

        timeTaken()
        stopWatchReset()
        return resultList
    }
}