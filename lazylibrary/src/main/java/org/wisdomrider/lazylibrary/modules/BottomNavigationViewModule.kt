@file:Suppress("UNREACHABLE_CODE")

package org.wisdomrider.lazylibrary.modules

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.wisdomrider.lazylibrary.Functions
import org.wisdomrider.lazylibrary.LazyModule
import org.wisdomrider.lazylibrary.R
import java.lang.Exception

class BottomNavigationViewModule(var textView: TextView? = null) : LazyModule() {

}

fun  BottomNavigationView.addBadge(index: Int): Functions<BottomNavigationViewModule> {
    return Functions(BottomNavigationViewModule::class.java) {
        var buttomNavigationMenuView = this.getChildAt(0) as BottomNavigationMenuView
        try {
            val view = buttomNavigationMenuView.getChildAt(index) as BottomNavigationItemView
            val badge = LayoutInflater.from(context).inflate(R.layout.merge, view, true)
             it.textView = badge.findViewById<TextView>(R.id.tv_notification)
        } catch (exception: Exception) {
            throw Exception("Index out of Bound Exception: model could not find menu on $index index")
        }
    }
}

fun BottomNavigationView.addNumber(index: Int): Functions<BottomNavigationViewModule> {
    return Functions(BottomNavigationViewModule::class.java) {
              if (index == 0) {
                  it.textView?.visibility = View.GONE
              }else {
                  it.textView?.visibility = View.VISIBLE
              }
              it.textView?.text = index.toString()
    }
}