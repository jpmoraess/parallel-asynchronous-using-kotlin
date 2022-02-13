package br.com.moraesit.parallelstreams

import br.com.moraesit.util.CommonUtil
import java.util.*
import java.util.stream.Collectors

class LinkedListSpliteratorExample {

    fun multiplyEachValue(inputList: LinkedList<Int>, multiplyValue: Int, isParallel: Boolean): List<Int> {
        CommonUtil.startTimer()
        val integerStream = inputList.stream() // sequential

        if (isParallel) integerStream.parallel() // parallel

        val resultList = integerStream.map { it * multiplyValue }
            .collect(Collectors.toList())

        CommonUtil.timeTaken()
        CommonUtil.stopWatchReset()
        return resultList
    }
}