package org.wisdomrider.lazylibrary.modules

import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import org.wisdomrider.lazylibrary.Functions
import org.wisdomrider.lazylibrary.LazyModule

class MapModule : LazyModule() {


}

fun FragmentActivity.createMapSync(id: Int, function: (GoogleMap?) -> Unit)
        : Functions<MapModule> {
    return Functions(MapModule::class.java) {
        var mapFragment = this.supportFragmentManager.findFragmentById(id) as SupportMapFragment
        mapFragment.getMapAsync { googleMap -> function(googleMap) }
    }
}
