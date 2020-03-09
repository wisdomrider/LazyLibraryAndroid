package org.wisdomrider.lazylibrary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

open class LazyModule {

    lateinit var lazy: LazyApp
    lateinit var context: Context

    constructor() {
    }

    fun setContext(lazy: LazyApp): LazyModule {
        this.lazy = lazy
        this.context = lazy.applicationContext
        return this

    }


}
