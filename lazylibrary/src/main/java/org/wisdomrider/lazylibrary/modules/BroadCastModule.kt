package org.wisdomrider.lazylibrary.modules

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import org.wisdomrider.lazylibrary.Functions
import org.wisdomrider.lazylibrary.LAZY_BROADCAST
import org.wisdomrider.lazylibrary.LazyModule

open class BroadCastModule : LazyModule() {

    override fun onInit() {
    }

}


fun Intent.sendBroadCast(): Functions<BroadCastModule> {
    return Functions(BroadCastModule::class.java) {
        this.action = LAZY_BROADCAST
        it.context.sendBroadcast(this)
    }
}

fun receiveBroadcast(
    functions: (intent: Intent?, receiver: BroadcastReceiver) -> Boolean
): Functions<BroadCastModule> {
    return Functions(BroadCastModule::class.java) {
        val intentFilter = IntentFilter(LAZY_BROADCAST)
        var receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (functions(intent, this))
                    it.context.unregisterReceiver(this)
            }

        }
        it.context.registerReceiver(receiver, intentFilter)
    }
}