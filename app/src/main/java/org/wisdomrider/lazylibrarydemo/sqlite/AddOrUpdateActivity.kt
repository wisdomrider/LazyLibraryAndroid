package org.wisdomrider.lazylibrarydemo.sqlite

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_or_update.*
import kotlinx.android.synthetic.main.dialog.*
import org.wisdomrider.lazylibrary.LazyBase
import org.wisdomrider.lazylibrary.lazyMap
import org.wisdomrider.lazylibrary.modules.sqlite.SQLITECONSTANTS.AND
import org.wisdomrider.lazylibrary.modules.sqlite.SQLITECONSTANTS.OR
import org.wisdomrider.lazylibrary.modules.sqlite.insert
import org.wisdomrider.lazylibrary.modules.sqlite.update
import org.wisdomrider.lazylibrary.modules.sqlite.updateTable
import org.wisdomrider.lazylibrary.modules.sqlite.where
import org.wisdomrider.lazylibrary.modules.toast
import org.wisdomrider.lazylibrarydemo.R
import org.wisdomrider.lazylibrarydemo.utils.BOOK
import org.wisdomrider.lazylibrarydemo.utils.IS_CALLED_FROM_UPDATE

class AddOrUpdateActivity : LazyBase() {
    var iscalledFromUpdate: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_or_update)
        supportActionBar!!.title = "Add or update Books"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var bundle = intent.extras
        iscalledFromUpdate = bundle[IS_CALLED_FROM_UPDATE] as Boolean

        if(iscalledFromUpdate) {
            var books = intent.extras[BOOK] as Books
            et_book_name.setText(books.name)
            et_book_author.setText(books.author)
            et_book_price.setText(books.price.toString())
            et_book_description.setText(books.description)
        }

        btn_insert_or_update.setOnClickListener {
            var bookName = et_book_name.text.toString()
            var bookAuthor = et_book_author.text.toString()
            var bookPrice = et_book_price.text.toString().toDouble()
            var bookDescription = et_book_description.text.toString()
            if (!iscalledFromUpdate) {
                // set property on Book object
                var books = Books(
                    name = bookName,
                    price = bookPrice,
                    author = bookAuthor,
                    description = bookDescription
                )
                addBooksOnsqliteDatabase(books)
            } else {
                var books = intent.extras[BOOK] as Books
                books.name = bookName
                books.author = bookAuthor
                books.price = bookPrice
                books.description = bookDescription
               // books.updateTable().lazy()
                books
                    .update(type = OR, condition = lazyMap("id" to  books.id

                        )
                        , autoInsert = false
                    ).lazy()



                "Update Sucessfully".toast().lazy()
                var intent = Intent(this, SqliteActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }


    private fun addBooksOnsqliteDatabase(books: Books) {
        books.insert().lazy()
        "Insert sucessfully".toast().lazy()
        var intent = Intent(this, SqliteActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        /* Books().createTable().lazy()
       Books().removeAll().lazy()
       Books("a2x", "'Book2", 10, 21312).insert().lazy()


       Books("a1x", "'Book2", 10, 21312)
           .update(type = OR, condition = lazyMap("id" to "a1x", "price" to 10)
           , autoInsert = false
           ).lazy()


       Books().where(type = AND, condition = lazyMap("id" to "a1x", "price" to 10)) {
           Log.e("UPDATE", "A")
       }.lazy()

       Books().delete(type = AND, condition = lazyMap("id" to "a1x", "price" to 10)).lazy()

       "select * from Books".rawQuery {
           Log.e("CURSOR",it.toString());
       }.lazy()*/

    }
}
