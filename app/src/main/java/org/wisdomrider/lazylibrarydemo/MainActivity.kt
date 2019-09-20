package org.wisdomrider.lazylibrarydemo

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog.*
import org.wisdomrider.lazylibrary.LazyBase

class MainActivity : LazyBase() {
    internal class API(
        var already: Int,
        var items: Int
    )

    lateinit var api: Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        t.loadImage(R.drawable.ic_launcher_foreground)
        lazy.initRetrofit(
            "https://misty-day.glitch.me",
            enableLogging = true,
            lazyNetworkDialog = RequestDialog()
        )
        api = lazy.retrofit.create(Api::class.java)




        api.a().fetch({

            loge(it!!.items)

        })
    }


    class RequestDialog : LazyNetworkDialog {
        override fun <T> onSucess(response: T?, ddialog: DataDialog, status: Int) {
            ddialog.dialog.gets_called.text = "ON Success";

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









