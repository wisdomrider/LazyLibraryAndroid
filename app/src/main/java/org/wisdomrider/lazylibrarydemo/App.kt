package org.wisdomrider.lazylibrarydemo

import org.wisdomrider.lazylibrary.LazyApp
import org.wisdomrider.lazylibrary.modules.MapModule
import org.wisdomrider.lazylibrary.modules.RecycleModule
import org.wisdomrider.lazylibrary.modules.RetrofitModule
import org.wisdomrider.lazylibrary.modules.ToastModule

class App : LazyApp() {
    lateinit var api: Api
    override fun onCreate() {
        super.onCreate()
        inject(ToastModule::class.java)
        inject(RecycleModule::class.java)
        inject(MapModule::class.java)
        api = inject(RetrofitModule::class.java)
            .buildRetrofit("https://jsonplaceholder.typicode.com", Api::class.java)
            .create(Api::class.java)


    }

}

