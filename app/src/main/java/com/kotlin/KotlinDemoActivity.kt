package com.kotlin

import android.os.Bundle
import com.ilifesmart.weather.R
import com.imou.BaseActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class KotlinDemoActivity : BaseActivity(), CoroutineScope by MainScope() {

    override fun layoutResId() = R.layout.kotlindemo_activity

    override fun initView() {}

    override fun initData() {
        println("initData ---------")

        val list = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,0)
        print("joinToString: ${joinToString(list, separator = ", ")}")
    }

    override fun onResume() {
        super.onResume()
//        coroutine00() // 基础00
//        coroutine01() // 获取数据并展示
//        coroutineCancel()
//        coroutine02()
//        coroutineContext()
//        coroutineAsync()
//        coroutineFlow()
        coroutineChannel()
    }

    fun coroutine00() = runBlocking {
        // launcher携程构造器
        GlobalScope.launch { // 启动一个新的协程并继续
            async {  }
            delay(1000L) // 延迟1S，非阻塞
            println("World")
        }
        println("hello,")
        delay(2000)
//        Thread.sleep(2000) // 阻塞主线程2S，保证JVM存活
    }

    fun coroutine01() {
        GlobalScope.launch {
//            coroutineScope {
//                val data = async(Dispatchers.IO) {
//                    delay(3000)
//                    4
//                }
//
//                withContext(Dispatchers.Main) {
//                    println("HAHHAHAHAHAH")
//                    val result = data.await()
//                    println("result: $result")
//                }
//            }
        }
    }

    fun coroutineCancel() = runBlocking {
        val job = launch {
            repeat(1000) {
                println("job: I'm sleeping $it")
                delay(500)
            }
        }

        delay(1300)
        println("Tired to cancel")
        job.cancelAndJoin()
//        job.join()
        println("now end_of_block")
    }

    fun coroutine02() = runBlocking{
        val startTime = System.currentTimeMillis()

        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i=0

            while (i < 5) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping$$$$$$$$$$$ ${i++} ...")
                    nextPrintTime += 500
                }
            }
        }

        delay(1300)
        println("main: I'm tired of waiting")
        job.cancelAndJoin() // 取消并等待它的结束.
        println("main: quit!")

    }

    fun coroutineContext() = runBlocking{
        launch {
            println("main runBlocking: ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Unconfined) {
            println("UnConfined: ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Default) {
            println("Default: ${Thread.currentThread().name}")
        }

        launch(Dispatchers.IO) {
            println("IO: ${Thread.currentThread().name}")
        }

//        launch(Dispatchers.Main) {
//            println("Main: ${Thread.currentThread().name}")
//        }
    }

    fun coroutineAsync() {
        fun foo() = listOf(1,2,3,4)
        foo().forEach { println("value: $it") }

        fun foo2() = sequence {
            for(i in 1..3) {
                Thread.sleep(100)
                yield(i)
            }
        }

        foo2().forEach {
            println("yield value:$it")
        }
    }

    fun coroutineFlow() {
        println("流-----")

        fun foo() = flow {
            for (i in 1..3) {
                delay(100)
                emit(i)
            }
        }

        runBlocking {
            launch {
                for(k in 1..3) {
                    println("I'm not blocked $k")
                    delay(100)
                }
            }.join()

//            foo().collect { println("flow: value $it")}
            foo().collect { println("flow: value $it") }
        }
    }

    fun coroutineChannel() = runBlocking {
        val channel = Channel<Int>()
        launch {
            for (x in 1..5) channel.send(x)
            channel.close() // 关闭通道，保证之前发出的值都被接收到
        }

        repeat(5) { println("channel-received: ${channel.receive()}")}
        println("Done!")

        coroutineProduce()
    }

    fun coroutineProduce() = runBlocking {
        fun sendChannel(): ReceiveChannel<Int> = produce {
            for(x in 1..5) send(x)
        }

        sendChannel().consumeEach { println("consumeWith: $it") }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}