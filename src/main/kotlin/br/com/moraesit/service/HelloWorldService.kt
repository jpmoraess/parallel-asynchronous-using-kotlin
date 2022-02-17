package br.com.moraesit.service

import br.com.moraesit.util.CommonUtil.Companion.delay

class HelloWorldService {

    fun helloWorld(): String {
        delay(1000)
        println("inside helloWorld")
        return "hello world"
    }

    fun hello(): String {
        delay(1000)
        println("inside hello")
        return "hello"
    }

    fun world(): String {
        delay(1000)
        println("inside world")
        return "world!"
    }
}