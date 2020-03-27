package com.catalin.library

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MessagesCall: ApiCall<MessagesService>(MessagesService::class.java) {
    fun getMessages(callback: (List<Message>) -> Unit) {
        apiCall(getService().getMessages().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            callback(it)
        })
    }
}