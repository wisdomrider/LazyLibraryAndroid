package org.wisdomrider.lazylibrary.modules

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wisdomrider.lazylibrary.Functions
import org.wisdomrider.lazylibrary.LazyModule
import java.util.*


open class RecycleModule : LazyModule() {


}

class LazyAdapter<T>(
    private var arraylist: ArrayList<T>,
    private var bindView: (holder: WisdomHolder, position: Int, item: T) -> Unit,
    private var onCreateViewHolder: (() -> WisdomHolder)? = null,
    private var view: Int? = null
) : RecyclerView.Adapter<LazyAdapter.WisdomHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisdomHolder {
        if (view == null) {
            return onCreateViewHolder?.let { it() }!!
        } else {
            return WisdomHolder(LayoutInflater.from(parent.context).inflate(view!!, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    override fun onBindViewHolder(holder: WisdomHolder, position: Int) {
        this.bindView(holder, position, arraylist[position])
    }

    open class WisdomHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}


fun <T> RecyclerView.lazyAdapter(
    arraylist: ArrayList<T>,
    onCreateViewHolder: () -> LazyAdapter.WisdomHolder,
    onBindViewHolder: (holder: LazyAdapter.WisdomHolder, position: Int, item: T) -> Unit
): LazyAdapter<T> {
    this.adapter = LazyAdapter(arraylist, onBindViewHolder, onCreateViewHolder, null)
    return this.adapter as LazyAdapter<T>
}

fun <T> RecyclerView.lazyAdapter(
    arraylist: ArrayList<T>,
    viewResource: Int,
    onBindViewHolder: (holder: LazyAdapter.WisdomHolder, position: Int, item: T) -> Unit
): LazyAdapter<T> {
    this.adapter = LazyAdapter(arraylist, onBindViewHolder, null, viewResource)
    return this.adapter as LazyAdapter<T>
}

fun RecyclerView.linearLayoutManager(): Functions<RecycleModule> {
    return Functions(RecycleModule::class.java) {
        this.layoutManager = LinearLayoutManager(it.context)
    }
}

fun RecyclerView.gridLayoutManager(perRow: Int): Functions<RecycleModule> {
    return Functions(RecycleModule::class.java) {
        this.layoutManager = GridLayoutManager(it.context, perRow)
    }
}





