package br.com.moraesit.parallelstreams

import java.util.stream.Collectors

fun main() {
    val inputList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    println("inputList: $inputList")
    val resultList = listOrder(inputList)
    println("resultList: $resultList")

    val inputSet = setOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    println("inputSet: $inputSet")
    val resultSet = setOrder(inputSet)
    println("resultSet: $resultSet")
}

fun listOrder(inputList: List<Int>): List<Int> {
    return inputList.parallelStream()
        .map { it * 2 }
        .collect(Collectors.toList())
}

fun setOrder(inputSet: Set<Int>): Set<Int> {
    return inputSet.parallelStream()
        .map { it * 2 }
        .collect(Collectors.toSet())
}