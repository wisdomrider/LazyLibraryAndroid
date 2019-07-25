package org.wisdomrider.lazylibrary

import android.app.Application

open class LazyApp : Application() {
    lateinit var lazy: Lazy
    override fun onCreate() {
        super.onCreate()
        lazy = Lazy(this)
    }

}
