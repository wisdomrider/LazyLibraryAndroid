package org.wisdomrider.lazylibrarydemo

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog.view.*
import org.wisdomrider.lazylibrary.LazyBase
import org.wisdomrider.lazylibrary.LazyRecyclerAdapter
import org.wisdomrider.lazylibrary.LazyViewHolder

class MainActivity : LazyBase() {
    lateinit var api: Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lazy.initRetrofit(
            "https://kiviaapi.herokuapp.com/",
            enableLogging = false,
            enableBasicAuthentication = true,
            userName = "hello@xc.me",
            password = "123456"
        )
        api = lazy.retrofit.create(Api::class.java)
        api.a().fetch(
            {
                recycler.layoutManager = LinearLayoutManager(this@MainActivity)
                recycler.adapter = LazyRecyclerAdapter(
                    R.layout.dialog, object : LazyViewHolder {
                        override fun lazyOnBindViewHolder(
                            holder: LazyRecyclerAdapter.WisdomHolder,
                            list: List<Any?>,
                            position: Int
                        ) {
                            var mylist = list as ArrayList<family>
                            var textView = holder.itemView.name
                            textView.text = mylist[position].name
                        }
                    }, it!!.family
                )
            }, { error ->
                Log.e("Error", error.message)
            }, true
        )
    }
}