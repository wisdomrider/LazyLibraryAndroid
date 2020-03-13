package org.wisdomrider.lazylibrarydemo

import org.wisdomrider.lazylibrary.LazyApp
import org.wisdomrider.lazylibrary.modules.RecycleModule
import org.wisdomrider.lazylibrary.modules.ToastModule

class App : LazyApp() {
    override fun onCreate() {
        super.onCreate()
        inject(ToastModule::class.java)
        inject(RecycleModule::class.java)

    }

}