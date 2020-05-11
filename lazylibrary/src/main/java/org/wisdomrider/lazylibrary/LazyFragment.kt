package org.wisdomrider.lazylibrary

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

class LazyFragment : Fragment() {

    lateinit var baseActivity: LazyBase
    lateinit var lazy: LazyApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity = this.activity as LazyBase
        lazy = baseActivity.lazy

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

    fun <T> openActivity(java: Class<T>? = null, intent: Intent? = null) {
        if (intent == null)
            startActivity(Intent(baseActivity, java))
        else
            startActivity(intent)
    }

    fun getLazyActivity(): LazyBase {
        return baseActivity
    }


}

