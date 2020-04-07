package org.wisdomrider.lazylibrarydemo.bottomnavigation

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_bottom_navigation_view.*
import org.wisdomrider.lazylibrary.LazyBase
import org.wisdomrider.lazylibrary.modules.NavigationItem
import org.wisdomrider.lazylibrary.modules.setBadges
import org.wisdomrider.lazylibrarydemo.R

class BottomNavigationView : LazyBase() {
    lateinit var navs: ArrayList<NavigationItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation_view)
        supportActionBar!!.title = "Bottom Navigation Module Example"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        navView.setBadges {
            navs = it
        }.lazy()

        navs[0].setCount(2)

    }
}

