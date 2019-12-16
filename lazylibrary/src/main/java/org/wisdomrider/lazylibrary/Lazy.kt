package org.wisdomrider.lazylibrary

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Lazy(var application: Application) {
    var context: Context = application.applicationContext
    lateinit var retrofit: Retrofit
    lateinit var base_url:String
   // lateinit var responseDialog: LazyBase.LazyNetworkDialog
    lateinit var mReceiver: BroadcastReceiver


    fun receiveBroadcast(function: (intent: Intent) -> Unit) {
        val intentFilter = IntentFilter(
            "com.wisdomrider.LazyAndroid"
        )
        mReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                function(intent)
            }
        }
        context.registerReceiver(mReceiver, intentFilter)
    }

    fun initRetrofit(
        BASE_URL: String,
        httpClient: OkHttpClient.Builder = OkHttpClient.Builder(),
        enableLogging: Boolean = false
       // lazyNetworkDialog: LazyBase.LazyNetworkDialog? = null
    ) {

        /*Must install implementation 'com.squareup.retrofit2:retrofit:2.6.1' */
        if (enableLogging) {
            val logging = HttpLoggingInterceptor()
            httpClient.addInterceptor(logging)
            logging.level = HttpLoggingInterceptor.Level.BODY
        }

       // lazyNetworkDialog?.let { responseDialog = lazyNetworkDialog }
        base_url=BASE_URL
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}

