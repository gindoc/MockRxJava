package com.gindoc.mockrxjava.lib

import com.gindoc.mockrxjava.lib.map.MapObservable
import com.gindoc.mockrxjava.lib.scheduler.ObserverObservable
import com.gindoc.mockrxjava.lib.scheduler.SubscribeObservable

/**
 * 真正观察者的包装类，连接各种操作符
 * @author :GIndoc
 * @date  :Created in 2021/2/19
 */
class MockObservable<T>(private val observable: MockObservableOnSubscribe<T>) {

    companion object {
        fun <T> create(observable: MockObservableOnSubscribe<T>): MockObservable<T> {
            return MockObservable(observable)
        }
    }

    fun subscribe(observer: MockObserver<T>) {
        observer.onSubscribe()
        observable.subscribe(observer)
    }

    fun <R> map(func: (T) -> R): MockObservable<R> {
        val map = MapObservable(observable, func)
        return MockObservable(map)
    }

    fun subscribeOn(thread:Int):MockObservable<T>{
        val observable = SubscribeObservable(observable, thread)
        return MockObservable(observable)
    }

    fun observeOn(thread: Int):MockObservable<T>{
        val observable = ObserverObservable(observable, thread)
        return MockObservable(observable)
    }
}