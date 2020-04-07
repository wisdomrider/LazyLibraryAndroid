package org.wisdomrider.lazylibrarydemo.mapactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.wisdomrider.lazylibrary.LazyBase
import org.wisdomrider.lazylibrary.modules.createMapSync
import org.wisdomrider.lazylibrary.modules.toast
import org.wisdomrider.lazylibrarydemo.R

class MapActivity : LazyBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        supportActionBar!!.title = "Map Module Example"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        this.createMapSync(R.id.map) {
            var lat = 27.712021
            var long = 85.312950
            it?.uiSettings?.setAllGesturesEnabled(true)
            val camaraUpdate = CameraUpdateFactory.newLatLngZoom(
                LatLng(lat, long), 16f
            )
            var marker = MarkerOptions().position(LatLng(lat, long)).title("Map Activity")
            it?.animateCamera(camaraUpdate)
            it?.addMarker(marker)
            "Map is ready".toast().lazy()
        }.lazy()
    }
}
