package com.xiaoke.bizhi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val imageView = ImageView(this)
            imageView.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            
            // 加载启动页图片
            val drawableId = resources.getIdentifier("splash_landscape", "drawable", packageName)
            if (drawableId != 0) {
                imageView.setImageResource(drawableId)
            }
            
            setContentView(imageView)
            
            // 延迟2秒后跳转
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 2000)
            
        } catch (e: Exception) {
            e.printStackTrace()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
