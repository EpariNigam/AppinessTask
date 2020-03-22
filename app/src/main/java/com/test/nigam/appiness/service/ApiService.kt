package com.test.nigam.appiness.service

import com.test.nigam.appiness.model.Item
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("api.json")
    fun getResponse(): Observable<List<Item>>
}