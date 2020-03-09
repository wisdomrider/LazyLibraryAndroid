package org.wisdomrider.lazylibrarydemo

import android.widget.Toast
import org.wisdomrider.lazylibrary.LazyApp

class App : LazyApp() {
    override fun onCreate() {
        super.onCreate()
        inject(ToastModule::class.java)

    }

}