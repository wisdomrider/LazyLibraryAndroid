package org.wisdomrider.lazylibrarydemo.api

import org.wisdomrider.lazylibrarydemo.fetchdata.FetchindDataFromServer
import retrofit2.Call
import retrofit2.http.GET
import java.util.*

interface Api {
    @GET("/posts")
    fun list(): Call<ArrayList<Response>>
    class Response(
        var userId: String,
        var id: String,
        var title: String,
        var body: String
    )
}