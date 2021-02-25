package com.gindoc.mockrxjava.lib.map

import com.gindoc.mockrxjava.lib.MockObservableOnSubscribe
import com.gindoc.mockrxjava.lib.MockObserver

/**
 * map操作符
 * @author :GIndoc
 * @date  :Created in 2021/2/19
 */
class MapObservable<T, R>(
    private val source: MockObservableOnSubscribe<T>,
    private val func: (T) -> R
) : MockObservableOnSubscribe<R> {

    override fun subscribe(observer: MockObserver<R>) {
        // 创建自己的观察者对象
        val map = MapObserver(observer, func)
        // 将自己传递给上游
        source.subscribe(map)
    }

    class MapObserver<T, R>(
        private val observer: MockObserver<R>,
        private val func: (T) -> R
    ) : MockObserver<T> {

        override fun onSubscribe() {
            observer.onSubscribe()
        }

        override fun onNext(t: T) {
            val result = func(t)
            observer.onNext(result)
        }

        override fun onComplete() {
            observer.onComplete()
        }

        override fun onError() {
            observer.onError()
        }

    }
}