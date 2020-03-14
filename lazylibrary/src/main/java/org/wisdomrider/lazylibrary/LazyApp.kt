package org.wisdomrider.lazylibrary

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

const val LAZY_BROADCAST = "LAZY_BROADCAST"

open class LazyApp : Application() {

    var callIt: ((intent: Intent?) -> Unit)? = null
    val filter = IntentFilter(LAZY_BROADCAST)
    lateinit var receiver: BroadcastReceiver
    private var modules = ArrayList<LazyModule>()


    override fun sendBroadcast(i: Intent) {
        i.action = LAZY_BROADCAST
        applicationContext.sendBroadcast(i)
    }

    fun getBroadcastStream() {

    }

    protected fun <T> inject(module: Class<T>): T {
        try {
            val module = (module.newInstance() as LazyModule).setContext(this)
            modules.add(module)
            return module as T
        } catch (exception: Exception) {
            throw LazyModuleNotFoundException(exception.message)
        }
    }

    fun <T> getModule(module: Class<T>): T {
        try {
            return (modules.filter { it.javaClass == module }[0] as T)
        } catch (e: Exception) {
            throw LazyModuleNotFoundException(e.message)
        }
    }

    override fun onCreate() {
        super.onCreate()
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (callIt == null) return
                callIt!!(intent)
            }

        }
    }


}


