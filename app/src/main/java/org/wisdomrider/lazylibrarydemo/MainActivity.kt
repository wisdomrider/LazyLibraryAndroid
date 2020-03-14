package org.wisdomrider.lazylibrarydemo

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*
import org.wisdomrider.lazylibrary.LazyBase
import org.wisdomrider.lazylibrary.modules.get
import org.wisdomrider.lazylibrary.modules.lazyAdapter
import org.wisdomrider.lazylibrary.modules.linearLayoutManager
import org.wisdomrider.lazylibrary.modules.toast

class MainActivity : LazyBase() {

    class Response(
        var userId: String,
        var id: String,
        var title: String,
        body: String
    )

    lateinit var api: Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        api = (application as App).api

        api.list()
            .get({
                it.body()!!.size.toString().toast().lazy()
//                lazy.sendBroadcast(Intent().putExtra("a", "w"))

            }, {
                it.message!!.toast().lazy()

            })

        recycle.linearLayoutManager().lazy()


    }

    override fun gotBroadcast(intent: Intent?) {
        "GOTSOME ${intent!!.extras.getString("a")}".toast().lazy()
    }

}






