package br.com.moraesit.forkjoin

import br.com.moraesit.util.CommonUtil
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveTask

class ForkJoinUseRecursion(inputList: List<String>) : RecursiveTask<List<String>>() {
    private var inputList: List<String>

    init {
        this.inputList = inputList
    }

    override fun compute(): List<String> {
        if (inputList.size <= 1) {
            val resultList = mutableListOf<String>()
            inputList.forEach { resultList.add(addNameLengthTransform2(it)) }
            return resultList
        }
        val midpoint = inputList.size / 2
        val leftInputList = ForkJoinUseRecursion(inputList.subList(0, midpoint)).fork()
        inputList = inputList.subList(midpoint, inputList.size)
        val rightResult = compute() // recursion happens
        val leftResult = leftInputList.join().toMutableList()
        leftResult.addAll(rightResult)
        return leftResult
    }
}

fun addNameLengthTransform2(name: String): String {
    CommonUtil.delay(500)
    return "${name.length}-${name}"
}

fun main() {
    CommonUtil.startTimer()

    val names = listOf("Dudu", "Deyverson", "Gabriel", "Patrick")
    val forkJoinPool = ForkJoinPool()
    val forkJoinUseRecursion = ForkJoinUseRecursion(names)
    val resultList = forkJoinPool.invoke(forkJoinUseRecursion)

    CommonUtil.timeTaken()
    println("ResultList: $resultList")
}