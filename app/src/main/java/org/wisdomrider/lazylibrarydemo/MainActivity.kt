package org.wisdomrider.lazylibrarydemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog.*
import kotlinx.android.synthetic.main.dialog.view.*
import org.wisdomrider.lazylibrary.LazyBase
import org.wisdomrider.lazylibrary.LazyRecyclerAdapter


class MainActivity : LazyBase() {
    internal class API(
        var already: Int,
        var items: Int
    )

    lateinit var api: Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lazy.initRetrofit(
            "https://kiviaapi.herokuapp.com/",
            enableLogging = true,
            lazyNetworkDialog = RequestDialog()
        )
        api = lazy.retrofit.create(Api::class.java)






        api.a().fetch({
            recycler.layoutManager = LinearLayoutManager(this@MainActivity)
            recycler.adapter=LazyRecyclerAdapter({
                _,_->

            },it!!.size,R.layout.dialog)


        })
    }


    class RequestDialog : LazyNetworkDialog {
        override fun <T> onSucess(response: T?, ddialog: DataDialog, status: Int) {
            ddialog.dialog.gets_called.text = "ON Success";
            ddialog.dialog.dismiss()

        }


        override fun onExceptions(ddialog: DataDialog, exception: Throwable) {
            ddialog.dialog.gets_called.text = exception.message
        }


        override fun onLoading(ddialog: DataDialog) {
            ddialog.dialog.setContentView(R.layout.dialog)
            ddialog.dialog.gets_called.text = "Loading..."
            ddialog.dialog.show()

        }


    }
}









