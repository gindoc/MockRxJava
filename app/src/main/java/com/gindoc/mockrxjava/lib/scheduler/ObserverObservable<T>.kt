package com.gindoc.mockrxjava.lib.scheduler

import com.gindoc.mockrxjava.lib.MockObservableOnSubscribe
import com.gindoc.mockrxjava.lib.MockObserver

/**
 * @author :GIndoc
 * @date  :Created in 2021/2/25
 */
class ObserverObservable<T>(
    private val source: MockObservableOnSubscribe<T>,
    private val thread: Int
) : MockObservableOnSubscribe<T> {

    override fun subscribe(observer: MockObserver<T>) {
        val mockObserver = ObserverObserver(observer, thread)
        source.subscribe(mockObserver)
    }

    class ObserverObserver<T>(
        private val observer: MockObserver<T>,
        private val thread: Int
    ) : MockObserver<T> {
        override fun onSubscribe() {
        }

        override fun onNext(t: T) {
            Schedulers.INSTANCE.submitObserverWork({observer.onNext(t)}, thread)
        }

        override fun onComplete() {
            Schedulers.INSTANCE.submitObserverWork({observer.onComplete()}, thread)
        }

        override fun onError() {
            Schedulers.INSTANCE.submitObserverWork({observer.onError()}, thread)
        }

    }
}