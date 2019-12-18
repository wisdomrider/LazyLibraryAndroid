package org.wisdomrider.lazylibrary

import android.app.Application
import android.content.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
import android.content.SharedPreferences

class Lazy(var application: Application) {
    var context: Context = application.applicationContext
    lateinit var retrofit: Retrofit
    lateinit var base_url: String
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
        enableLogging: Boolean? = false,
        enableBasicAuthentication: Boolean? = false,
        userName: String? = "",
        password: String? = "",
        httpClient: OkHttpClient.Builder? = OkHttpClient.Builder()
    ) {
        initLazyPref(context)
        /*Must install implementation 'com.squareup.retrofit2:retrofit:2.6.1' */
        if (enableLogging!!) {
            val logging = HttpLoggingInterceptor()
            httpClient!!.addInterceptor(logging)
            logging.level = HttpLoggingInterceptor.Level.BODY
        }

        if (enableBasicAuthentication!!)
            httpClient!!
                .addInterceptor(BasicAuthInterceptor(userName!!, password!!))
                .build()

        val requestInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder().addHeader(
                prefs?.getString(LazyConstant.TOKEN_HEADER, ""),
                prefs?.getString(LazyConstant.TOKEN_VALUE, "")
            )
                .build()
            return@Interceptor chain.proceed(request)
        }

        httpClient!!.addInterceptor(requestInterceptor)

        base_url = BASE_URL
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient!!.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    var prefs: SharedPreferences? = null
    private fun initLazyPref(context: Context): SharedPreferences {
        val prefs = context.applicationContext.getSharedPreferences(LazyConstant.LAZY_PREFERENCE, 0)
        this.prefs = prefs
        return prefs
    }

    fun lazyInterceptor(tokenHeader: String, tokenValue: String) {
        prefs?.edit()?.putString(LazyConstant.TOKEN_HEADER, tokenHeader)?.apply()
        prefs?.edit()?.putString(LazyConstant.TOKEN_VALUE, tokenValue)?.apply()
    }
}