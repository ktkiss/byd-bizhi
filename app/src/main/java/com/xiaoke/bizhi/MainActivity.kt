package com.xiaoke.bizhi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TextView textView = new TextView(this);
        textView.setText("小可壁纸 v1.0.82 - 加载中");
        textView.setTextSize(24);
        textView.setGravity(android.view.Gravity.CENTER);
        
        setContentView(textView);
    }
}