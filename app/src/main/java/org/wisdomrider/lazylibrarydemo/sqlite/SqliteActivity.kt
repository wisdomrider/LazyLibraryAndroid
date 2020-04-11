package org.wisdomrider.lazylibrarydemo.sqlite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_sqlite.*
import kotlinx.android.synthetic.main.row_books.view.*
import org.wisdomrider.lazylibrary.LazyBase
import org.wisdomrider.lazylibrary.modules.LazyAdapter
import org.wisdomrider.lazylibrary.modules.lazyAdapter
import org.wisdomrider.lazylibrary.modules.linearLayoutManager
import org.wisdomrider.lazylibrary.modules.sqlite.*
import org.wisdomrider.lazylibrary.modules.toast
import org.wisdomrider.lazylibrarydemo.R
import org.wisdomrider.lazylibrarydemo.utils.BOOK
import org.wisdomrider.lazylibrarydemo.utils.IS_CALLED_FROM_UPDATE

class SqliteActivity : LazyBase() {
    lateinit var adapter: LazyAdapter<Books>
    lateinit var list: ArrayList<Books>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)
        supportActionBar!!.title = "Sq lite Example"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // Creating book table on Sq lite database
        Books().createTable().lazy()
        // Getting all book
        getAllBooksFromDatabase()
        adapter = rv_books.lazyAdapter(list, R.layout.row_books)
        { wisdomHolder: LazyAdapter.WisdomHolder, index: Int, books: Books ->
            wisdomHolder.itemView.tv_book_name.text = books.name
            wisdomHolder.itemView.tv_book_price.text = "$ ${books.price}"
            wisdomHolder.itemView.tv__book_description.text = books.description
            wisdomHolder.itemView.tv__book_author.text = books.author
            wisdomHolder.itemView.btn_update.setOnClickListener {
                updateBooks(books)
            }

            wisdomHolder.itemView.btn_delete.setOnClickListener {
                books.deleteOne().lazy()
                list.removeAt(index)
                adapter.notifyItemRemoved(index)
                /*getAllBooksFromDatabase()
                adapter.notifyDataSetChanged()*/

            }
        }
        rv_books.linearLayoutManager().lazy()
        btn_add_books.setOnClickListener {
            var intent = Intent(this, AddOrUpdateActivity::class.java)
            var bundle = Bundle()
            bundle.putBoolean(IS_CALLED_FROM_UPDATE, false)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        btn_remove_all.setOnClickListener {
            removeAllBooksFromDatabase()
        }
    }


    private fun updateBooks(book: Books) {
        var intent = Intent(this, AddOrUpdateActivity::class.java)
        var bundle = Bundle()
        bundle.putBoolean(IS_CALLED_FROM_UPDATE, true)
        bundle.putSerializable(BOOK,book)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun removeAllBooksFromDatabase() {
        Books().removeAll().lazy()
        list.clear()
        adapter.notifyDataSetChanged()
        getAllBooksFromDatabase()
    }

    private fun getAllBooksFromDatabase() {
        Books().getAll() {
            list = it!!
            if (it.size == 0) {
                "No data on Database".toast().lazy()
            }
        }.lazy()
    }
}
