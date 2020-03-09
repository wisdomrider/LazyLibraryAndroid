package org.wisdomrider.lazylibrarydemo

import android.os.Bundle
import org.wisdomrider.lazylibrary.Functions
import org.wisdomrider.lazylibrary.LazyBase

class MainActivity : LazyBase() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        "WELCOME_WISD".toast().extensive()
        extensives { arrayOf("".toast(), "".toast(), "".toast(), "".toast(), "".toast()) }


    }



}


