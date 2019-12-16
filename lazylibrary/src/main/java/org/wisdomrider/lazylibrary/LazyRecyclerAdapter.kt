package org.wisdomrider.lazylibrary

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

 class LazyRecyclerAdapter(
     val inflateView: Int? = null,
     val lazyViewHolder: LazyViewHolder,
     val list: List<Any>
) :
    Adapter<LazyRecyclerAdapter.WisdomHolder>(), LazyViewHolder {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisdomHolder {
        return WisdomHolder(View.inflate(parent.context, inflateView!!, null))
    }

    class WisdomHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: WisdomHolder, position: Int) {
        this.lazyViewHolder.lazyOnBindViewHolder(holder,list,position)
    }

    override fun getItemCount(): Int {
         return list.size
    }
}
