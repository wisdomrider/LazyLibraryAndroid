package org.wisdomrider.lazylibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Functions<T>(
    var java: Class<T>,
    var function: (T) -> Unit
)

open class LazyBase : AppCompatActivity() {
    lateinit var lazy: LazyApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lazy = application as LazyApp

    }

    fun <T> getModule(module: Class<T>): T {
        return lazy.getModule(module)
    }


    fun <T> lazy(d: () -> Functions<T>) {
        val x = d()
        x.function(lazy.getModule(x.java))
    }

    fun <T> lazies(d: () -> Array<Functions<T>>) {
        d().forEach {
            it.function(lazy.getModule(it.java))
        }
    }

    fun <T> Functions<T>.lazy(): Any {
        return this.function(lazy.getModule(this.java))
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