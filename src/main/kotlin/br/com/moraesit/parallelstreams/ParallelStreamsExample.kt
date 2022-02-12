package br.com.moraesit.parallelstreams

import br.com.moraesit.util.CommonUtil
import java.util.stream.Collectors

fun main() {
    val names = listOf("Dudu", "Deyverson", "Gabriel", "Veiga")
    CommonUtil.startTimer()
    //val transformedNamesList = stringTransform(names)
    val transformedNamesList = stringTransform_parellel(names)
    println("List: $transformedNamesList")
    CommonUtil.timeTaken()
}

fun stringTransform(namesList: List<String>): List<String> {
    return namesList
        .stream()
        .map { addNameLengthTransform(it) }
        .collect(Collectors.toList())
}

fun stringTransform_parellel(namesList: List<String>): List<String> {
    return namesList
        .stream()
        .parallel()
        .map { addNameLengthTransform(it) }
        .collect(Collectors.toList())
}

fun addNameLengthTransform(name: String): String {
    CommonUtil.delay(500)
    return "${name.length}-${name}"
}