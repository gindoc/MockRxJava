package com.gindoc.mockrxjava.lib

/**
 * 被观察者
 * @author :GIndoc
 * @date  :Created in 2021/2/19
 */
interface MockObservableOnSubscribe<T> {
    fun subscribe(observer: MockObserver<T>)
}