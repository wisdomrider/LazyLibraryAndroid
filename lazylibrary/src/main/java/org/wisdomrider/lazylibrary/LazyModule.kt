package org.wisdomrider.lazylibrary

import android.content.Context
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

open class LazyModule {
    lateinit var lazy: LazyApp
    lateinit var context: Context

    constructor() {
    }

    fun setContext(lazy: LazyApp): LazyModule {
        this.lazy = lazy
        this.context = lazy.applicationContext
        this.onInit()
        return this
    }

    open fun onInit() {

    }


}
