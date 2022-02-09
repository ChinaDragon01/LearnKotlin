package com.test.learnkotlin.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.test.learnkotlin.http.ResponseEntity
import com.test.learnkotlin.http.exception.ExceptionEngine
import com.test.learnkotlin.http.exception.HttpException
import com.test.learnkotlin.utils.ReflexUtil2
import com.test.learnkotlin.utils.SingleLiveEvent
import kotlinx.coroutines.*

/*

    Kotlin Coroutines(协程) 完全解析（二），深入理解协程的挂起、恢复与调度
    https://www.jianshu.com/p/2979732fb6fb

    Android开发之MVVM模式实践：协程与网络请求的结合
    https://blog.csdn.net/m0_58350991/article/details/117330291

    Kotlin 协程与 JetPack 组件初探
    https://blog.csdn.net/weixin_38754349/article/details/108353799

    https://blog.csdn.net/u013790519/article/details/107259597/
 */
open class BaseViewModel<Model : BaseModel>(application: Application) :
    AndroidViewModel(application), DefaultLifecycleObserver {
    val loadingEvent = SingleLiveEvent<Boolean>()
    open val model: Model by lazy(LazyThreadSafetyMode.NONE) {
        ReflexUtil2.getTypeInstance(this, IModel::class.java) as Model
    }

    fun addToObserver(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    /**
     * 启动网络请求
     */
    fun <T> launchHttp(
        block: suspend CoroutineScope.() -> ResponseEntity<T>,
        response: (ResponseEntity<T>) -> Unit,
        start: suspend CoroutineScope.() -> Unit = {},
        complete: suspend CoroutineScope.() -> Unit = {},
        showLoading: Boolean = false
    ) = launch {
        request(
            start,
            { response(withContext(Dispatchers.IO) { block() }) },
            { response(ResponseEntity(ExceptionEngine.handleException(it))) },
            { complete() },
            showLoading
        )
    }

    //block（）即为我们的网络请求方法
    fun launch(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch { block() }


    //block（）即为我们的网络请求方法
    fun launchOnUI(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launch(Dispatchers.Main) { block() }

    fun launchOnIO(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launch(Dispatchers.IO) { block() }

    /**
     * 网络请求
     */
    private suspend fun request(
        start: suspend CoroutineScope.() -> Unit,
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(HttpException) -> Unit,
        complete: suspend CoroutineScope.() -> Unit,
        showLoading: Boolean = false
    ) = coroutineScope {
        try {
            if (showLoading) loadingEvent.value = true
            start()
            block()
        } catch (e: Throwable) {
            error(ExceptionEngine.handleException(e))
        } finally {
            complete()
            if (showLoading) loadingEvent.value = false
        }
    }

    sealed class launchHttpState {
        object Loading : launchHttpState()
        object Fail : launchHttpState()
        object Except : launchHttpState()
    }
}