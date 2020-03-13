package org.wisdomrider.lazylibrarydemo

import android.util.Log
import org.wisdomrider.lazylibrary.Functions
import org.wisdomrider.lazylibrary.modules.ToastModule

class ToastModule2 : ToastModule() {
    var isADLOaded = false

    fun toastForOneMinute(s: String) {
        toast(s, 60000)
        isADLOaded = true

    }

}


fun String.Toast(): Functions<ToastModule2> {
    return Functions(ToastModule2::class.java) {
        it.toastForOneMinute(this)
        Log.e("LOG", it.isADLOaded.toString())

    }
}
