package org.wisdomrider.lazylibrary

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import android.graphics.Color
import android.os.Build
import android.view.WindowManager
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

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

    lateinit var boradcastReciver: BroadcastReceiver

    fun extractDigits(src: String): String {
        val builder = StringBuilder()
        for (i in 0 until src.length) {
            val c = src[i]
            if (Character.isDigit(c)) {
                builder.append(c)
            }
        }
        return builder.toString()
    }



    fun Intent.sendToBroadcast() {
        this.action = "com.wisdomrider.LazyAndroid"
        sendBroadcast(this)
    }

    fun <T> Call<T>.fetch(
        response: (response: Response<T>) -> Unit,
        failure: ((t: Throwable) -> Unit)? = null,
        showProgressBar: Boolean? = false,
        progressBarTittle: String? = "Loading ...",
        progressBarColor: String? = "#000000",
        fetchData: Boolean? = true
    ) {
        if (fetchData!!) {
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
                       response(response)

                }
            })
        }
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


    private fun checkReadSMSPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkSelfPermission(android.Manifest.permission.READ_SMS)== PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

     fun getReadSMSPermission() {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             if (!checkReadSMSPermission()) {
                 requestPermissions(arrayOf(android.Manifest.permission.READ_SMS), 305)
                 return
             } else {
                 registerBraodCast()
             }
         } else {
             registerBraodCast()
         }

    }

    lateinit var pinView: LazyPinView
    lateinit var phoneNumber: String

    fun enableOneTimeOtpCode(phonenumber: String, pinView: LazyPinView) {
        this.pinView = pinView
        this.phoneNumber = phonenumber
        getReadSMSPermission()
    }
    fun registerBraodCast() {
        boradcastReciver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                var msg = intent!!.getStringExtra(IncomingSms.SMS_MESSAGE)
                var phonenumbers = intent!!.getStringExtra(IncomingSms.PHONE_NUMBER)
                if (phonenumbers == phoneNumber) {
                    var code = extractDigits(msg)
                    pinView.setText(code)
                }
            }
        }
        var intentfilter = IntentFilter("com.sms.broadcast")
        registerReceiver(boradcastReciver, intentfilter)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (305 == requestCode) {
                registerBraodCast()
            }
        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            if (305 == requestCode) {
               Toast.makeText(this, "REQUEST_READ_SMS_PERMISSION Permisssion Not Granted By User", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

