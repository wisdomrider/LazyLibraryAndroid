package org.wisdomrider.lazylibrary

import android.app.Application
import android.content.pm.PackageManager

open class LazyApp : Application() {

    private var modules = ArrayList<LazyModule>()

    override fun onCreate() {
        super.onCreate()

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


}


