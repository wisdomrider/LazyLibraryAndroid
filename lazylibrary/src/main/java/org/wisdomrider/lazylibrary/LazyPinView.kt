package org.wisdomrider.lazylibrary

import android.content.BroadcastReceiver
import android.content.Context
import android.util.AttributeSet

class LazyPinView : com.chaos.view.PinView {
    lateinit var boradcastReciver: BroadcastReceiver

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }


}
