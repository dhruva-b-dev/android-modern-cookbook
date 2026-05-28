package com.dhruva.kotlinflow.ui.flowon

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.kotlinflow.di.DefaultDispatcher
import com.dhruva.kotlinflow.di.IoDispatcher
import com.dhruva.kotlinflow.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlowOnViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    companion object {
        private const val TAG = "FlowOnViewModel"
    }

    fun startFlowOnTask() {
        viewModelScope.launch(mainDispatcher) {
            doLongRunningTask()
                .flowOn(defaultDispatcher)
                .filter {
                    printThreadName("filter 1")
                    return@filter true
                }
                .map {
                    printThreadName("map 1")
                    return@map it * it
                }
                .flowOn(defaultDispatcher)
                .filter {
                    printThreadName("filter 2")
                    return@filter true
                }
                .flowOn(mainDispatcher)
                .map {
                    printThreadName("map 2")
                    return@map it * it
                }
                .flowOn(defaultDispatcher)
                .filter {
                    printThreadName("filter 3")
                    return@filter true
                }
                .map {
                    printThreadName("map 3")
                    return@map it * it
                }
                .collect {
                    printThreadName("collect")
                }
        }
    }

    private fun doLongRunningTask(): Flow<Int> {
        return flow {
            printThreadName("doLongRunningTask")
            delay(2000)
            emit(2)
        }
    }

    private fun printThreadName(src: String) {
        Log.d(TAG, src + " : " + Thread.currentThread().name)
    }
}