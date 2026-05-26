package com.dhruva.kotlincoroutines.ui.basic

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.dhruva.kotlincoroutines.ui.components.CommonButtonComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

private const val TAG = "BasicScreen"

@Preview
@Composable
fun BasicScreen(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()

    // Create and remember the scroll state
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonButtonComponent(
            text = "testCoroutine",
            onClick = { testCoroutine(scope) }
        )

        CommonButtonComponent(
            text = "testCoroutineWithMain",
            onClick = { testCoroutineWithMain(scope) }
        )

        CommonButtonComponent(
            text = "testCoroutineWithMainImmediate",
            onClick = { testCoroutineWithMainImmediate(scope) }
        )

        CommonButtonComponent(
            text = "testCoroutineEverything",
            onClick = { testCoroutineEverything(scope) }
        )

        CommonButtonComponent(
            text = "usingGlobalScope",
            onClick = { usingGlobalScope(scope) }
        )

        CommonButtonComponent(
            text = "testSuspending",
            onClick = { testSuspending(scope) }
        )

        CommonButtonComponent(
            text = "testBlocking",
            onClick = { testBlocking(scope) }
        )

        CommonButtonComponent(
            text = "twoLaunches",
            onClick = { twoLaunches(scope) }
        )

        CommonButtonComponent(
            text = "withErrorExceptionHandler",
            onClick = { withErrorExceptionHandler(scope) }
        )

        CommonButtonComponent(
            text = "withNoErrorExceptionHandler",
            onClick = { withNoErrorExceptionHandler(scope) }
        )

        CommonButtonComponent(
            text = "exceptionInAsyncBlock",
            onClick = { exceptionInAsyncBlock(scope) }
        )

        CommonButtonComponent(
            text = "exceptionInLaunchBlock",
            onClick = { exceptionInLaunchBlock(scope) }
        )

        CommonButtonComponent(
            text = "exceptionInAsyncBlockWithAwait",
            onClick = { exceptionInAsyncBlockWithAwait(scope) }
        )
    }
}

private fun testCoroutine(scope: CoroutineScope) {
    Log.d(TAG, "Function starts...")
    scope.launch {
        Log.d(TAG, "before task...")
        doLongRunningTask()
        Log.d(TAG, "before task...")
    }
    Log.d(TAG, "function end...")
}

private fun testCoroutineWithMain(scope: CoroutineScope) {
    Log.d(TAG, "Function starts...")
    scope.launch(Dispatchers.Main) {
        Log.d(TAG, "before task...")
        doLongRunningTask()
        Log.d(TAG, "before task...")
    }
    Log.d(TAG, "function end...")
}

private fun testCoroutineWithMainImmediate(scope: CoroutineScope) {
    Log.d(TAG, "Function starts...")
    scope.launch(Dispatchers.Main.immediate) {
        Log.d(TAG, "before task...")
        doLongRunningTask()
        Log.d(TAG, "before task...")
    }
    Log.d(TAG, "function end...")
}

private fun testCoroutineEverything(scope: CoroutineScope) {
    Log.d(TAG, "Function starts...")
    scope.launch(Dispatchers.Main) {
        Log.d(TAG, "before task 1...")
        doLongRunningTask()
        Log.d(TAG, "after task 1...")
    }

    scope.launch(Dispatchers.Main) {
        Log.d(TAG, "before task 2...")
        doLongRunningTask()
        Log.d(TAG, "after task 2...")
    }

    Log.d(TAG, "function end...")
}

private fun usingGlobalScope(scope: CoroutineScope) {

    Log.d(TAG, "Function starts...")

    GlobalScope.launch {
        Log.d(TAG, "before task")
        doLongRunningTask()
        Log.d(TAG, "after task")
    }

    Log.d(TAG, "Function ends...")
}

private val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

private fun testSuspending(scope: CoroutineScope) {
    scope.launch(dispatcher) {
        Log.d(TAG, "testSuspending before task 1")
        doLongRunningTask()
        Log.d(TAG, "testSuspending after task 1")
    }
    scope.launch(dispatcher) {
        Log.d(TAG, "testSuspending before task 2")
        doLongRunningTask()
        Log.d(TAG, "testSuspending after task 2")
    }
}

private fun testBlocking(scope: CoroutineScope) {
    scope.launch(dispatcher) {
        runBlocking {
            Log.d(TAG, "testSuspending before task 1")
            doLongRunningTask()
            Log.d(TAG, "testSuspending after task 1")
        }
    }
    scope.launch(dispatcher) {
        runBlocking {
            Log.d(TAG, "testSuspending before task 2")
            doLongRunningTask()
            Log.d(TAG, "testSuspending after task 2")
        }
    }
}

private fun twoLaunches(scope: CoroutineScope) {
    Log.d(TAG, "Function Start")

    scope.launch(Dispatchers.Default) {
        Log.d(TAG, "Before Delay 1")
        delay(2000)
        Log.d(TAG, "After Delay 1")
    }

    scope.launch(Dispatchers.Default) {
        Log.d(TAG, "Before Delay 2")
        delay(2000)
        Log.d(TAG, "After Delay 2")
    }

    Log.d(TAG, "Function End")
}

private val exceptionHandler = CoroutineExceptionHandler { _, e ->
    Log.d(TAG, "coroutine exception handler $e")
}

fun withErrorExceptionHandler(scope: CoroutineScope) {
    Log.d(TAG, "function start")
    scope.launch(exceptionHandler) {
        Log.d(TAG, "before task")
        doLongRunningTask()
        throw Exception("error")
        Log.d(TAG, "after task")
    }
    Log.d(TAG, "function end")
}

fun withNoErrorExceptionHandler(scope: CoroutineScope) {
    Log.d(TAG, "function start")
    scope.launch(exceptionHandler) {
        Log.d(TAG, "before task")
        doLongRunningTask()
        Log.d(TAG, "after task")
    }
    Log.d(TAG, "function end")
}

private fun exceptionInLaunchBlock(scope: CoroutineScope) {
    scope.launch {
        doSomethingAndThrowException()
    }
}

private fun exceptionInAsyncBlock(scope: CoroutineScope) {
    scope.async {
        doSomethingAndThrowException()
    }
}

private fun exceptionInAsyncBlockWithAwait(scope: CoroutineScope) {
    scope.launch {
        val deferred = scope.async(Dispatchers.Default) {
            doSomethingAndThrowException()
            return@async 10
        }
        try {
            val result = deferred.await()
        } catch (e: Exception) {
            Log.d(TAG, "exception handler: $e")
        }
    }
}

private fun doSomethingAndThrowException() {
    throw Exception("Some Exception")
}


private suspend fun doLongRunningTask() {
    withContext(Dispatchers.Default) {
        Log.d(TAG, "Before delay")
        delay(2000)
        Log.d(TAG, "after delay")
    }
}

private suspend fun timeTakingTask() {
    withContext(Dispatchers.IO) {
        // your code for doing a time taking task
        // Added delay to simulate
        Thread.sleep(5000)
    }
}

private suspend fun doLongRunningTaskOne(): Int {
    return withContext(Dispatchers.Default) {
        // your code for doing a long running task
        // Added delay to simulate
        delay(2000)
        return@withContext 10
    }
}

private suspend fun doLongRunningTaskTwo(): Int {
    return withContext(Dispatchers.Default) {
        // your code for doing a long running task
        // Added delay to simulate
        delay(2000)
        return@withContext 10
    }
}