package com.gindoc.mockrxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.gindoc.mockrxjava.lib.MockObservable
import com.gindoc.mockrxjava.lib.MockObservableOnSubscribe
import com.gindoc.mockrxjava.lib.MockObserver
import com.gindoc.mockrxjava.lib.scheduler.Schedulers

/**
 * 参考https://mp.weixin.qq.com/s/TsyB7oXgQSCPyUP7r7SuKg
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MockObservable.create(object :MockObservableOnSubscribe<String>{
            override fun subscribe(observer: MockObserver<String>) {
                observer.onNext("hello, it's mock rxJava")
            }
        })
            .subscribe(object :MockObserver<String>{
                override fun onSubscribe() {
                    Log.e(MainActivity::class.simpleName, "onSubscribe")
                }

                override fun onNext(t: String) {
                    Log.e(MainActivity::class.simpleName, "onNext receive content: $t")
                }

                override fun onComplete() {
                }

                override fun onError() {
                }

            })


        MockObservable.create(object :MockObservableOnSubscribe<Int>{
            override fun subscribe(observer: MockObserver<Int>) {
                Log.e(MainActivity::class.simpleName, "subscribe() current thread is ${Thread.currentThread()}")
                observer.onNext(666)
            }

        })
            .map {
                Log.e(
                    MainActivity::class.simpleName,
                    "map() current thread is ${Thread.currentThread()}"
                )
                "$it map to \"$it\""
            }
            // 由第一个subscribeOn()所指定的线程决定之前的observable的subscribe()方法运行在哪个线程，
            // 例如此例子MapObservable.subscribe()运行在IOSubscribeObservable所指定的线程，
            // 无论IOSubscribeObservable之后被subscribeOn()了几次（即无论IOSubscribeObservable的subscribe()运行在哪个线程），
            // 最终MapObservable.subscribe()还是运行在IOSubscribeObservable所指定的线程（即IO线程）
            .subscribeOn(Schedulers.IO)
            .subscribeOn(Schedulers.MAIN)

            // observeOn()只作用于它之后的observable，它之前的observable还是执行在它原来所处的线程
            // 无论observable之前有多少个observeOn()，它只运行在它之前的最后一个observeOn()所指定的线程，原理同subscribeOn()
            .map {
                Log.e(MainActivity::class.simpleName, "map() before observeOn() current thread is ${Thread.currentThread()}")
                "test for observeOn(), origin data is $it"
            }
            .observeOn(Schedulers.MAIN)
            .map {
                Log.e(MainActivity::class.simpleName, "map() after observeOn() current thread is ${Thread.currentThread()}")
                "test for observeOn(), and origin data is $it"
            }
            .observeOn(Schedulers.IO)
            .subscribe(object :MockObserver<String>{
                override fun onSubscribe() {
                    Log.e(MainActivity::class.simpleName, "onSubscribe() current thread is ${Thread.currentThread()}")
                    Log.e(MainActivity::class.simpleName, "onSubscribe")
                }

                override fun onNext(t: String) {
                    Log.e(MainActivity::class.simpleName, "onNext() current thread is ${Thread.currentThread()}")
                    Log.e(MainActivity::class.simpleName, "onNext receive content: $t")
                }

                override fun onComplete() {

                }

                override fun onError() {

                }

            })
    }
}