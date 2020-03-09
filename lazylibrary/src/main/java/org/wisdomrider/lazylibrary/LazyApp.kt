package org.wisdomrider.lazylibrary

import android.app.Application

open class LazyApp : Application() {

    private var modules = ArrayList<LazyModule>()

    override fun onCreate() {
        super.onCreate()

    }

    protected fun <T> inject(module: Class<T>) {
        try {
            modules.add((module.newInstance() as LazyModule).setContext(this))
        } catch (exception: Exception) {
            throw LazyModuleNotFoundException()
        }
    }

    fun <T> getModule(module: Class<T>): T {
        try {
            return (modules.filter { it.javaClass == module }[0] as T)
        } catch (e: Exception) {
            throw LazyModuleNotFoundException()
        }
    }


}


