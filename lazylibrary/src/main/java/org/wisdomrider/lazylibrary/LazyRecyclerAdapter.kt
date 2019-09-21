package org.wisdomrider.lazylibrary

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter



public class LazyRecyclerAdapter(
    var bindViewHolder: (holder: WisdomHolder, position: Int) -> Unit,
    var items: Int,
    val inflateView: Int? = null,
    var viewHolderCreate: ((parent: ViewGroup, viewType: Int) -> WisdomHolder)? = null
) :
    Adapter<LazyRecyclerAdapter.WisdomHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisdomHolder {
        viewHolderCreate?.let { a -> return a(parent, viewType) }
        return WisdomHolder(View.inflate(parent.context, R.layout.dialog, null))
    }
    class WisdomHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: WisdomHolder, position: Int) {
        bindViewHolder(holder, position)
    }

    override fun getItemCount(): Int {
        return items
    }
}
