package com.catalin.library

import io.reactivex.Observable
import retrofit2.http.GET

interface MessagesService {
    @GET("/assets/cmx/us/messages/collections.json")
    fun getMessages(): Observable<List<Message>>
}