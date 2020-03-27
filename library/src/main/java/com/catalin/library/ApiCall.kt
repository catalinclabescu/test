package com.catalin.library

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class ApiCall<S>(private val serviceClass: Class<S>) {
    private var service: S? = null
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun getService(): S = service?: ServiceGenerator.createService(serviceClass) 

    protected fun apiCall(call: Disposable) {

        compositeDisposable.add(call)
    }
    fun clear() = run { compositeDisposable.clear() }
    
}