package org.wisdomrider.lazylibrarydemo

import org.wisdomrider.lazylibrary.LazyApp
import org.wisdomrider.lazylibrary.modules.*
import org.wisdomrider.lazylibrary.modules.sqlite.SqliteModule
import org.wisdomrider.lazylibrarydemo.api.Api

class App : LazyApp() {
    lateinit var api: Api
    override fun onCreate() {
        super.onCreate()
        inject(ToastModule::class.java)
        inject(RecycleModule::class.java)
        inject(MapModule::class.java)
        inject(BroadCastModule::class.java)
        inject(BottomNavigationModule::class.java)
        api = inject(RetrofitModule::class.java)
            .build("https://jsonplaceholder.typicode.com", Api::class.java)
            .create(Api::class.java)
        inject(SqliteModule::class.java)
            .build("DB_NAME")
    }
}

