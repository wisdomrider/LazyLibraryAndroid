package org.wisdomrider.lazylibrarydemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import org.wisdomrider.lazylibrary.LazyBase
import org.wisdomrider.lazylibrary.modules.receiveBroadcast
import org.wisdomrider.lazylibrary.modules.sendBroadCast
import org.wisdomrider.lazylibrary.modules.sqlite.SqliteClosedHelper
import org.wisdomrider.lazylibrary.modules.sqlite.SqliteModule
import org.wisdomrider.lazylibrary.modules.toast
import org.wisdomrider.lazylibrarydemo.bottomnavigation.BottomNavigationView
import org.wisdomrider.lazylibrarydemo.fetchdata.FetchindDataFromServer
import org.wisdomrider.lazylibrarydemo.mapactivity.MapActivity
import java.lang.reflect.Field

class MainActivity : LazyBase() {

    class W(s: String) {
        val s: Int = 1

        constructor() : this("")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sqlite = SqliteClosedHelper(this, "dbname")
        sqlite.createTable(W::class.java)

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
                receiveBroadcast { it, _ ->
                    it!!.extras.getString("key").toast().lazy()
                    true
                }.lazy()
                val intent = Intent()
                intent.putExtra("key", "Hello broadCast !")
                intent.sendBroadCast().lazy()

            }

            R.id.action_bottom_nav_view -> {
                openActivity(BottomNavigationView::class.java)
            }

        }
        return super.onOptionsItemSelected(item)
    }


}
