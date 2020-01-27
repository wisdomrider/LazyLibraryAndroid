package org.wisdomrider.lazylibrary

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import retrofit2.Retrofit

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
}