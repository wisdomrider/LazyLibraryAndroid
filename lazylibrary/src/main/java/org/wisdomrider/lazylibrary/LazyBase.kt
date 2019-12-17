package org.wisdomrider.lazylibrary

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import android.graphics.Color
import android.widget.LinearLayout
import android.view.WindowManager
import android.widget.TextView
import android.view.Gravity
import android.view.ViewGroup

open class LazyBase : AppCompatActivity() {

    lateinit var lazy: Lazy
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lazy = Lazy(application)
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
        response: (response: T?) -> Unit,
        failure: ((t: Throwable) -> Unit)? = null,
        showProgressBar: Boolean? = false,
        progressBarTittle: String? = "Loading ...",
        progressBarColor: String? = "#000000"
    ) {
        var progressBar = getProgressBar(progressBarTittle!!, progressBarColor!!)
        if (showProgressBar!!)
            progressBar.show()
        this.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                if (showProgressBar!!)
                    progressBar.dismiss()
                failure?.let { failure(t) }
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (showProgressBar!!)
                    progressBar.dismiss()
                if (response.code() == 200) {
                    response(response.body())
                } else if (response.code() == 401) {
                    failure?.let { failure(Throwable("Unauthorized")) }
                } else {
                    failure?.let { failure(Throwable("Un Known Error")) }
                }
            }
        })
    }

    fun getProgressBar(progressBarTittle: String, progressBarColor: String): AlertDialog {
        val llPadding = 30
        val ll = LinearLayout(this)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam
        val progressBar = ProgressBar(this)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(this)
        tvText.text = progressBarTittle
        tvText.setTextColor(Color.parseColor(progressBarColor))
        tvText.textSize = 20f
        tvText.layoutParams = llParam

        ll.addView(progressBar)
        ll.addView(tvText)

        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setView(ll)
        val dialog = builder.create()
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window.attributes = layoutParams
        }
        return dialog
    }
}

