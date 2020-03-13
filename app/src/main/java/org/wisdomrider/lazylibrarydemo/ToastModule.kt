package org.wisdomrider.lazylibrarydemo

import android.widget.Toast
import org.wisdomrider.lazylibrary.Functions
import org.wisdomrider.lazylibrary.LazyModule

open class ToastModule : LazyModule() {


    fun toast(s: String, time: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, s, time).show()
        lazy.getModule()
    }
}


fun String.toast(): Functions<ToastModule> {
    return Functions(ToastModule::class.java) {
        it.toast(this)
    }

}




