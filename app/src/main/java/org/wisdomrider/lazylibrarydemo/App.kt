package org.wisdomrider.lazylibrarydemo

import android.app.Application

class App : Application() {
    class H(var x: String)

    lateinit var h: H
    override fun onCreate() {
        super.onCreate()
        h = H("demo")
    }
}
