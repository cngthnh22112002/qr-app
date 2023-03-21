package com.example.myqrscanapp.api

import com.example.myqrscanapp.Models.ResponseListProducts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    val get:String
    @GET("/{path}.json?auth.uid=fskf2515ddaa@vdv")
    suspend fun getAllProducts(@Path("path") id: String?): Response<ResponseListProducts>
}

