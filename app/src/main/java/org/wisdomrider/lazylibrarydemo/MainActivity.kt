package org.wisdomrider.lazylibrarydemo

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*
import org.wisdomrider.lazylibrary.LazyBase
import org.wisdomrider.lazylibrary.modules.gridLayoutManager
import org.wisdomrider.lazylibrary.modules.lazyAdapter
import org.wisdomrider.lazylibrary.modules.linearLayoutManager

class MainActivity : LazyBase() {
    class Book(
        var id: Int,
        var name: String,
        var price: Float
    )

    var books = ArrayList<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        books.add(Book(1, "a", 1.2f))
        books.add(Book(2, "b", 1.1f))
        books.add(Book(3, "c", 1.0f))
        books.add(Book(4, "d", 1.3f))
        recycle.lazyAdapter(books, R.layout.item) { holder, position, item ->
            holder.itemView.book_id.text = item.id.toString()
            holder.itemView.book_name.text = item.name
        }
        recycle.gridLayoutManager(2).lazy()

    }


}


