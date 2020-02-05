package org.wisdomrider.lazylibrarydemo

import org.wisdomrider.lazylibrary.LazyApp

class App : LazyApp(
    "https://kiviaapi.herokuapp.com/",
    enableLogin = true,
    enableBasicAuthentication = false,
    userName = "hello@xc.me",
    password = "123456"
) {
    lateinit var api: Api
    override fun onCreate() {
        super.onCreate()
        api = retrofit.create(Api::class.java)
    }
}
