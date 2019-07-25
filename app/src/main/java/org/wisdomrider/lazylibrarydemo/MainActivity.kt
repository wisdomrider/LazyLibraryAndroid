package org.wisdomrider.lazylibrarydemo

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.wisdomrider.lazylibrary.LazyBase

class MainActivity : LazyBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        t.update()

    }

}

