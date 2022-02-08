package com.test.learnkotlin.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

object AppExecutors {

    /** 最大线程数 */
    private const val MAX_THREAD_COUNT = 6

    private val mMainThread: MainThreadExecutor by lazy { MainThreadExecutor() }
    private val mDiskIO: ScheduledExecutorService by lazy { Executors.newScheduledThreadPool(1) }
    private val mWorkerThread: Executor by lazy { Executors.newFixedThreadPool(MAX_THREAD_COUNT) }

    fun diskIO(): Executor {
        return mDiskIO
    }

    fun workerThread(): Executor {
        return mWorkerThread
    }

    fun mainThread(): Executor {
        return mMainThread
    }

    /**
     * 在IO线程中执行给定的任务
     */
    fun executeOnDiskIO(runnable: Runnable?) {
        mDiskIO.execute(runnable)
    }

    /**
     * 在IO线程池中执行延迟任务
     *
     * @param task  任务
     * @param delay 延迟开始
     */
    fun executeDelayedOnDiskIO(task: Runnable?, delay: Long, unit: TimeUnit?) {
        mDiskIO.schedule(task, delay, unit)
    }

    /**
     * 在IO线程池中执行周期性的任务
     *
     * @param task   任务
     * @param delay  延迟开始
     * @param period 执行周期
     */
    fun executePeriodicOnDiskIO(task: Runnable?, delay: Long, period: Long, unit: TimeUnit?) {
        mDiskIO.scheduleAtFixedRate(task, delay, period, unit)
    }

    /**
     * 在工作线程中执行给定的任务
     */
    fun executeOnWorkerThread(runnable: Runnable?) {
        mWorkerThread.execute(runnable)
    }

    /**
     * 在主线程上执行给定的任务
     *
     *
     * 如果当前线程是主线程，则立即运行给定的任务；否则提交给主线程执行
     */
    fun executeOnMainThread(runnable: Runnable) {
        if (isMainThread) {
            runnable.run()
        } else {
            postToMainThread(runnable)
        }
    }

    /**
     * 将给定的任务提交到主线程执行
     */
    fun postToMainThread(runnable: Runnable) {
        mMainThread.execute(runnable)
    }

    /**
     * 将给定的任务提交到主线程执行
     */
    fun postDelayedToMainThread(runnable: Runnable, delayMillis: Long) {
        mMainThread.executeDelayed(runnable, delayMillis)
    }

    /**
     * 判断当前线程是否为主线程
     */
    val isMainThread: Boolean
        get() = Looper.getMainLooper().thread === Thread.currentThread()

    private class MainThreadExecutor : Executor {
        private val mMainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mMainThreadHandler.post(command)
        }

        fun executeDelayed(command: Runnable, delayMillis: Long) {
            mMainThreadHandler.postDelayed(command, delayMillis)
        }
    }
}