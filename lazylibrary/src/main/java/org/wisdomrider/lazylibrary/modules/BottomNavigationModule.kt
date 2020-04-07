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

class NavigationItem(var view: View) {

    fun removeCount(): NavigationItem {
        this.view.findViewById<TextView>(R.id.count).visibility = View.GONE
        return this
    }

    fun setCount(count: Int, background: Int? = null, hideAtZero: Boolean = true) {
        val item = view.findViewById<TextView>(R.id.count)
        if (count == 0 && hideAtZero)
            item.visibility = View.GONE
        else {
            item.visibility = View.VISIBLE
            item.text = "$count"
        }
        if (background != null)
            item.setBackgroundResource(background)
    }
}

class BottomNavigationModule : LazyModule() {

}


fun BottomNavigationView.setBadges(func: (list: ArrayList<NavigationItem>) -> Unit): Functions<BottomNavigationModule> {
    return Functions(BottomNavigationModule::class.java) { module ->
        var items = (this.getChildAt(0) as BottomNavigationMenuView)
        var list = ArrayList<NavigationItem>()
        repeat(items.childCount) {
            var badge = LayoutInflater
                .from(module.context)
                .inflate(R.layout.badge, items.getChildAt(it) as BottomNavigationItemView, true)
            list.add(NavigationItem(badge).removeCount())
        }
        func(list)
    }
}
