package org.wisdomrider.lazylibrarydemo.bottomnavigation
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_bottom_navigation_view.*
import org.wisdomrider.lazylibrary.LazyBase
import org.wisdomrider.lazylibrary.modules.addBadge
import org.wisdomrider.lazylibrary.modules.updateNumberOnBadge
import org.wisdomrider.lazylibrarydemo.R

class BottomNavigationView : LazyBase() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation_view)
        customBottomBar.addBadge(0).lazy()
        // Now we can update badges from any where
        customBottomBar.updateNumberOnBadge(3).lazy()
    }
}