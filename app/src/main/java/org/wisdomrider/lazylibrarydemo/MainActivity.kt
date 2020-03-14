package org.wisdomrider.lazylibrarydemo

import android.content.Intent
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.wisdomrider.lazylibrary.LazyBase
import org.wisdomrider.lazylibrary.modules.createMapSync
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
                it.body()!!.size.toString().toast().lazy()
//                lazy.sendBroadcast(Intent().putExtra("a", "w"))

            }, {
                it.message!!.toast().lazy()

            })

        recycle.linearLayoutManager().lazy()*/


        this.createMapSync(R.id.map) {

             var lat = 27.712021
            var long = 85.312950
            it?.uiSettings?.setAllGesturesEnabled(true)
            val camaraUpdate = CameraUpdateFactory.newLatLngZoom(
                LatLng(lat,long), 16f
            )
            var marker = MarkerOptions().position(LatLng(lat,long)).title("Map Activity")
            it?.animateCamera(camaraUpdate)
            it?.addMarker(marker)
            "Map is ready".toast().lazy()
        }.lazy()
    }

    override fun gotBroadcast(intent: Intent?) {
        "GOTSOME ${intent!!.extras.getString("a")}".toast().lazy()
    }

}
