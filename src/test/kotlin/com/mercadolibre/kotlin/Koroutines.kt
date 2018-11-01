package com.mercadolibre.kotlin

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

import java.util.concurrent.Executors

class Koroutines {

    @Test
    fun `Must print World after Hello`() {
        GlobalScope.launch {
            delay(1000L)
            println("World!")
        }
        GlobalScope.launch {
            println("FIRST!") //This may or may not come after Hello
        }

        println("Hello")
        Thread.sleep(2000L) //<- without this the JVM will die

        println("Somente depois do block")
    }

    @Test
    fun `Using running block and awaiting without a time interval`() {
        runBlocking {
            val job = launch {
                delay(100L)
                println("World!")
            }
            launch {
                println("FIRST!") //This may or may not come after Hello
            }

            println("Hello")
            job.join()

            println("Somente depois do block")
        }
    }

    //Terminou em 4 segundos
    @Test
    fun `Launches 100k coroutines`() {
        runBlocking {
            repeat(100_000) {
                // launch a lot of coroutines
                launch {
                    delay(1000L)
                    print(".")
                }
            }
        }
    }

    @Test
    fun `Create 100k threads on a pool`(){
        assertThrows<OutOfMemoryError> {
            //Out of memory
            val threadPool = Executors.newFixedThreadPool(100_000)
            for (i in 0..100_000) threadPool.execute(Runnable { print("!") })
        }
    }

    //Terminpou em 2 segundos
    @Test
    fun `Run 100_000 comands on chached thread pool`() {
        val executor = Executors.newCachedThreadPool()
        for (i in 0..100_000) executor.execute(Runnable { print("!") })
    }
}