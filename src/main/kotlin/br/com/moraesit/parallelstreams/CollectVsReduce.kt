package br.com.moraesit.parallelstreams

import java.util.stream.Collectors

fun main() {
    val names = listOf("João", "Pedro", "Moraes", "Andressa", "Cristina")

    val collect = collect(names) // better performance compared to reduce function
    println("collect: $collect")

    val reduce = reduce(names)
    println("reduce: $reduce")
}

fun collect(names: List<String>) = names.parallelStream().collect(Collectors.joining())

fun reduce(names: List<String>) = names.parallelStream().reduce("") { s1, s2 -> s1 + s2 }