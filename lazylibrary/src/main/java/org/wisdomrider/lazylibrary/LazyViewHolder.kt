package org.wisdomrider.lazylibrary

interface LazyViewHolder {
    fun lazyOnBindViewHolder(holder: LazyRecyclerAdapter.WisdomHolder, list: List<Any?>,position: Int) {

    }
}
