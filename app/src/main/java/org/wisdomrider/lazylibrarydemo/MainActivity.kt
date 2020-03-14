package org.wisdomrider.lazylibrarydemo

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*
import org.wisdomrider.lazylibrary.LazyBase
import org.wisdomrider.lazylibrary.modules.lazyAdapter
import org.wisdomrider.lazylibrary.modules.linearLayoutManager
import org.wisdomrider.lazylibrary.modules.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                recycle.lazyAdapter(it.body()!!, R.layout.item) { holder, position, item ->
                    holder.itemView.user_id.text = item.userId
                    holder.itemView.id1.text = item.title
                }
            }, {
                it.message!!.toast().lazy()

            })

        recycle.linearLayoutManager().lazy()


    }


}


}



