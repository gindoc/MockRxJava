package com.gindoc.mockrxjava.lib.scheduler

import com.gindoc.mockrxjava.lib.MockObservableOnSubscribe
import com.gindoc.mockrxjava.lib.MockObserver

/**
 * @author :GIndoc
 * @date  :Created in 2021/2/25
 */
class SubscribeObservable<T>(
    private val source: MockObservableOnSubscribe<T>,
    private val thread: Int
) : MockObservableOnSubscribe<T> {

    override fun subscribe(observer: MockObserver<T>) {
        Schedulers.INSTANCE.submitSubscribeWork(source, observer, thread)
    }

}