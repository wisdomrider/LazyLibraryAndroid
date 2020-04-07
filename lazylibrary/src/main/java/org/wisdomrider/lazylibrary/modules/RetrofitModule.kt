package org.wisdomrider.lazylibrary.modules

import okhttp3.OkHttpClient
import org.wisdomrider.lazylibrary.LazyModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitModule : LazyModule() {
    lateinit var retrofit: Retrofit
    var httpClient: OkHttpClient? = null
    fun <T> buildRetrofit(baseUrl: String, apiClass: Class<T>): Retrofit {
        var retrofitBefore = Retrofit.Builder()
        retrofitBefore.baseUrl(baseUrl)
        retrofitBefore.addConverterFactory(GsonConverterFactory.create())
        if (httpClient != null) {
            retrofitBefore.client(this.httpClient!!)
        }
        retrofit = retrofitBefore.build()
        return retrofit
    }

    fun setInterceptor(httpClient: OkHttpClient) {
        this.httpClient = httpClient
    }

}

fun <T> Call<T>.get(
    response: (response: Response<T>) -> Unit,
    failure: (t: Throwable) -> Unit
) {
    this.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            failure(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            response(response)
        }
    })
}



