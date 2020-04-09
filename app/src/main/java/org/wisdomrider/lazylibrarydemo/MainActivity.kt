package org.wisdomrider.lazylibrarydemo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import org.wisdomrider.lazylibrary.LazyBase
import org.wisdomrider.lazylibrary.modules.receiveBroadcast
import org.wisdomrider.lazylibrary.modules.sendBroadCast
import org.wisdomrider.lazylibrary.modules.sqlite.SQLITECONSTANTS.AND
import org.wisdomrider.lazylibrary.modules.sqlite.SqliteAnnotations
import org.wisdomrider.lazylibrary.modules.sqlite.createTable
import org.wisdomrider.lazylibrary.modules.sqlite.insert
import org.wisdomrider.lazylibrary.modules.sqlite.where
import org.wisdomrider.lazylibrary.modules.toast
import org.wisdomrider.lazylibrarydemo.bottomnavigation.BottomNavigationView
import org.wisdomrider.lazylibrarydemo.fetchdata.FetchindDataFromServer
import org.wisdomrider.lazylibrarydemo.mapactivity.MapActivity

class MainActivity : LazyBase() {

    class Books(
        @SqliteAnnotations.Primary
        var id: String,
        var name: String,
        var price: Int,
        var stock: Long
    ) {
        constructor() : this("a1x", "", 1, 2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Books().createTable().lazy()
        Books("a1x", "'Book2", 10, 21312).insert().lazy()
        Books().where(type = AND, condition = hashMapOf("id" to "a1x", "price" to 10)) {

        }.lazy()
        Books().delete(type=AND,condition= hashMapOf("id" to "a1x")){

        }

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




