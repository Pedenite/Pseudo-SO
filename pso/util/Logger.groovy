package pso.util

abstract class Logger {
    static final def error = "\u001B[31m"
    static final def warning = "\u001B[33m"
    static final def success = "\u001B[32m"
    static final def normal = "\u001B[0m"

    static def error(message){
        println("${error}${message}${normal}")
        System.exit(-1)
    }

    static def warning(message){
        println("${warning}${message}${normal}")
    }

    static def success(message){
        println("${success}${message}${normal}")
    }
}