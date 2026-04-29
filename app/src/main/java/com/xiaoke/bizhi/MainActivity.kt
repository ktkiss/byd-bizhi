package com.xiaoke.bizhi

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 最简单的测试 - 只显示一个 TextView
        val textView = TextView(this)
        textView.text = "小可壁纸 - 加载中..."
        textView.textSize = 24f
        textView.gravity = android.view.Gravity.CENTER

        setContentView(textView)
    }
}