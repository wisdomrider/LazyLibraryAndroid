package org.wisdomrider.lazylibrarydemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import org.wisdomrider.lazylibrary.LazyBase
import org.wisdomrider.lazylibrary.modules.reciveLazyBroadCast
import org.wisdomrider.lazylibrary.modules.sendLazyBoradcast
import org.wisdomrider.lazylibrarydemo.fetchdata.FetchindDataFromServer
import org.wisdomrider.lazylibrarydemo.mapactivity.MapActivity

class MainActivity : LazyBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.example_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_map -> {
                openActivity(MapActivity::class.java)
            }
            R.id.action_fetchingdata -> {
                openActivity(FetchindDataFromServer::class.java)
            }
            R.id.action_broad_cast_receiver -> {
               // Receiving broad cast Example
                reciveLazyBroadCast() { context: Context?, intent: Intent?,reciver: BroadcastReceiver? ->
                    var msg = intent?.getStringExtra("key")
                    Log.e("message", msg)
                    // if you want un register than you can
                    unregisterReceiver(reciver)
                }.lazy()
                // Sending Broad cast Example
                var intent = Intent()
                intent.putExtra("key", "Hello broad cast")
                sendLazyBoradcast(intent).lazy()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun <T> openActivity(java: Class<T>) {
        var intent = Intent(this, java)
        startActivity(intent)
    }
}
