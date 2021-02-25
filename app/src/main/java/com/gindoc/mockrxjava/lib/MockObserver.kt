package com.gindoc.mockrxjava.lib

/**
 * 观察者
 * @author :GIndoc
 * @date  :Created in 2021/2/19
 */
interface MockObserver<T> {
    fun onSubscribe()
    fun onNext(t:T)
    fun onComplete()
    fun onError()

}