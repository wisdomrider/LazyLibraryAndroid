package org.wisdomrider.lazylibrary

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Lazy(var application: Application) {

    var context: Context = application.applicationContext
    lateinit var retrofit: Retrofit
    val gson = Gson()
    fun toast(str: String, time: Int = Toast.LENGTH_SHORT): Any = Toast.makeText(context, str, time).show()
    fun initRetrofit(BASE_URL: String, httpClient: OkHttpClient.Builder) {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}

