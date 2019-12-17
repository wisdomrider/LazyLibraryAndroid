package org.wisdomrider.lazylibrary

import java.lang.Exception

interface LazyViewHolder {
    fun lazyOnBindViewHolder(holder: LazyRecyclerAdapter.WisdomHolder, list: List<Any?>,position: Int)


}
