package org.wisdomrider.lazylibrary

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

open class LazyBase : AppCompatActivity() {

    lateinit var lazy: Lazy
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lazy = (application as LazyApp).lazy
    }

    fun TextView.update() {
        this.setOnClickListener { lazy.toast("asd") }
    }

}

