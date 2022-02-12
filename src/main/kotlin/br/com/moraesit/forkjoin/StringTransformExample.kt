package br.com.moraesit.forkjoin

import br.com.moraesit.util.CommonUtil

fun main() {
    CommonUtil.startTimer()

    val resultList = mutableListOf<String>()
    val names = listOf("Dudu", "Deyverson", "Gabriel", "Patrick")
    names.forEach { resultList.add(addNameLengthTransform(it)) }

    CommonUtil.timeTaken()

    println("Final Result: $resultList")
}

fun addNameLengthTransform(name: String): String {
    CommonUtil.delay(500)
    return "${name.length}-${name}"
}