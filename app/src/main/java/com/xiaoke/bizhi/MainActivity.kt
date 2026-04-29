package com.xiaoke.bizhi

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this)
        textView.text = "小可壁纸 - 加载中..."
        textView.textSize = 24f

        setContentView(textView)
    }
}