package com.gindoc.mockrxjava.lib.scheduler

import android.os.Handler
import android.os.Looper
import com.gindoc.mockrxjava.lib.MockObservableOnSubscribe
import com.gindoc.mockrxjava.lib.MockObserver
import java.util.concurrent.Executors

/**
 * @author :GIndoc
 * @date  :Created in 2021/2/25
 */
class Schedulers {

    private val threadPool = Executors.newCachedThreadPool()
    private val handler = Handler(Looper.getMainLooper())

    companion object {
        val IO = 1
        val MAIN = 2

        val INSTANCE by lazy { Schedulers() }
    }

    fun <T> submitSubscribeWork(
        source: MockObservableOnSubscribe<T>,
        observer: MockObserver<T>,
        thread: Int
    ) {
        when (thread) {
            IO -> {
                threadPool.submit { source.subscribe(observer) }
            }
            MAIN -> {
                handler.post {
                    source.subscribe(observer)
                }
            }
        }
    }

    fun submitObserverWork(func: () -> Unit, thread: Int) {
        when (thread) {
            IO -> {
                threadPool.submit { func.invoke() }
            }
            MAIN -> {
                handler.post {
                    func.invoke()
                }
            }
        }
    }
}