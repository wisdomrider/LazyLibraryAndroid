package org.wisdomrider.lazylibrary

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable


open class LazyBase : AppCompatActivity() {

    lateinit var lazy: Lazy
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lazy =Lazy(application)
    }


    fun ImageView.loadImage(any: Any, loadInstant: Boolean = true): RequestBuilder<Drawable> {
        /* Must install implementation 'com.github.bumptech.glide:glide:4.9.0' */
        var glide = Glide.with(this.context)
            .load(any)
        if (loadInstant)
            glide.into(this)
        return glide
    }

    fun Serializable.sendToBroadcast() {
        val intent = Intent("com.wisdomrider.LazyAndroid")
            .putExtra("data", this)
        sendBroadcast(intent)
    }


    fun Intent.sendToBroadcast() {
        this.action = "com.wisdomrider.LazyAndroid"
        sendBroadcast(this)
    }

    fun <T> Call<T>.fetch(
        context: Context,
        response: (response: T?) -> Unit,
        failure: ((t: Throwable) -> Unit)? = null,
        showDialog: Boolean = true
    ) {
        var dialog = DataDialog(context)
        dialog.url = this.request().url().toString().replace(lazy.base_url, "")

        if (showDialog)
         //   lazy.responseDialog.onLoading(dialog)

        this.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                if (showDialog)
           //         lazy.responseDialog.onExceptions(dialog, t)
                failure?.let { failure(t) }
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (showDialog)
             //       lazy.responseDialog.onSucess(response.body(), dialog, response.code())
                    response(response.body())
            }

        })
    }

    class DataDialog(context: Context, var url: String = "") {

        var dialog: Dialog = Dialog(context)

    }

    interface LazyNetworkDialog {
        /*Gets called when the request is being sent */
        /*You can make a loading or something you wanted*/
        fun onLoading(ddialog: DataDialog)

        /*Called when you are on onResponse*/
        /*Used for showing different errors or something*/
        fun <T> onSucess(response: T?, ddialog: DataDialog, status: Int)

        /*Called when retrofit throws an exception*/
        fun onExceptions(ddialog: DataDialog, exception: Throwable)
    }
}

