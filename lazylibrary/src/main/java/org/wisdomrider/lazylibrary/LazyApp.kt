package org.wisdomrider.lazylibrary

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class LazyApp(
    val BASE_URL: String, val enableLogin: Boolean? = false
    , val enableBasicAuthentication: Boolean? = false, val userName: String? = "",
    val password: String? = "",
    var httpClient: OkHttpClient.Builder? = OkHttpClient.Builder()
) : Application() {
    lateinit var retrofit: Retrofit
    lateinit var base_url: String

    override fun onCreate() {
        super.onCreate()
        initRetrofit(BASE_URL, enableLogin, enableBasicAuthentication, userName, password, httpClient)
    }

    fun initRetrofit(
        BASE_URL: String,
        enableLogging: Boolean? = false,
        enableBasicAuthentication: Boolean? = false,
        userName: String? = "",
        password: String? = "",
        httpClient: OkHttpClient.Builder? = OkHttpClient.Builder()
    ) {
        initLazyPref(applicationContext)
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

    fun <T> Call<T>.fetch(
        response: (response: Response<T>) -> Unit,
        failure: ((t: Throwable) -> Unit)? = null,
        showProgressBar: Boolean? = false,
        progressBarTittle: String? = "Loading ...",
        progressBarColor: String? = "#000000",
        fetchData: Boolean? = true
    ) {
        if (fetchData!!) {
            var progressBar = getProgressBar(progressBarTittle!!, progressBarColor!!)
            if (showProgressBar!!)
                progressBar.show()
            this.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    if (showProgressBar!!)
                        progressBar.dismiss()
                    failure?.let { failure(t) }
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (showProgressBar!!)
                        progressBar.dismiss()
                    response(response)

                }
            })
        }
    }

    fun getProgressBar(progressBarTittle: String, progressBarColor: String): AlertDialog {
        val llPadding = 30
        val ll = LinearLayout(this)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam
        val progressBar = ProgressBar(this)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(this)
        tvText.text = progressBarTittle
        tvText.setTextColor(Color.parseColor(progressBarColor))
        tvText.textSize = 20f
        tvText.layoutParams = llParam

        ll.addView(progressBar)
        ll.addView(tvText)

        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setView(ll)
        val dialog = builder.create()
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window.attributes = layoutParams
        }
        return dialog
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
