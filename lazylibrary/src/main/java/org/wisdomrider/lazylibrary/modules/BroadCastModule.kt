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
}

fun FragmentActivity.sendLazyBoradcast(intent: Intent): Functions<BroadCastModule> {
    return Functions(BroadCastModule::class.java) {
        intent.action = LAZY_BROADCAST
        this.sendBroadcast(intent)
    }
}

fun FragmentActivity.reciveLazyBroadCast (
     functions: (context: Context?, intent: Intent?, reciver: BroadcastReceiver) -> Unit
): Functions<BroadCastModule> {
    return Functions(BroadCastModule::class.java) {
        val intentFilter = IntentFilter(
            LAZY_BROADCAST
        )
        var receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                functions(context, intent, this as BroadcastReceiver)
            }

        }
        this.registerReceiver(receiver, intentFilter)
    }
}