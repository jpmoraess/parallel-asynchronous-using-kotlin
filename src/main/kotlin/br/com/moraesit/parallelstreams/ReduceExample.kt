package br.com.moraesit.parallelstreams

class ReduceExample {
    fun reduceSumParallelStream(inputList: List<Int>): Int {
        return inputList.parallelStream().reduce(0) { x, y -> x + y }
    }

    fun reduceMultiplyParallelStream(inputList: List<Int>): Int {
        return inputList.parallelStream().reduce(1) { x, y -> x * y }
    }
}

