package org.wisdomrider.lazylibrarydemo.fetchdata

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_fetchind_data_from_server.*
import kotlinx.android.synthetic.main.item.view.*
import org.wisdomrider.lazylibrary.LazyBase
import org.wisdomrider.lazylibrary.modules.*
import org.wisdomrider.lazylibrarydemo.App
import org.wisdomrider.lazylibrarydemo.R
import org.wisdomrider.lazylibrarydemo.api.Api
import java.util.ArrayList

class FetchindDataFromServer : LazyBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetchind_data_from_server)
        supportActionBar!!.title="Retrofit and Recycler Module Example"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var api = (application as App).api

        api.list()
            .get({
                 rv_fetchdata.lazyAdapter(it.body()!!, R.layout.item)
                 { wisdomHolder: LazyAdapter.WisdomHolder, postion: Int, response: Api.Response ->
                     wisdomHolder.itemView.tv_user_id.text =  "User id ${response.userId}"
                     wisdomHolder.itemView.tv_title.text = "${response.title}"
                     wisdomHolder.itemView.tv_body.text = "${response.body}"
                 }
            }, {
                it.message!!.toast().lazy()

            })

        rv_fetchdata.linearLayoutManager().lazy()
    }
}

