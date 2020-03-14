package org.wisdomrider.lazylibrarydemo

import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*
import org.wisdomrider.lazylibrary.LazyBase
import org.wisdomrider.lazylibrary.modules.createMapSync
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

      /*  api = (application as App).api

        api.list()
            .get({
                recycle.lazyAdapter(it.body()!!, R.layout.item) { holder, position, item ->
                    holder.itemView.user_id.text = item.userId
                    holder.itemView.id1.text = item.title
                }
            }, {
                it.message!!.toast().lazy()

            })

        recycle.linearLayoutManager().lazy()*/


        this.createMapSync(R.id.map) {

            /* var lat = 27.712021
            var long = 85.312950
            it?.uiSettings?.setAllGesturesEnabled(true)
            val camaraUpdate = CameraUpdateFactory.newLatLngZoom(
                LatLng(lat,long), 16f
            )
            var marker = MarkerOptions().position(LatLng(lat,long)).title("Map Activity")
            it?.animateCamera(camaraUpdate)
            it?.addMarker(marker)
            "Map is ready".toast().lazy()*/

        }.lazy()


    }





}






