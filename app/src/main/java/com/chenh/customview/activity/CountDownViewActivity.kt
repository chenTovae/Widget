package com.chenh.customview.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.chenh.customview.R
import com.chenh.customview.view.CountDownView

class CountDownViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down_view)

        val mCountDownView = findViewById(R.id.count_view) as CountDownView
        mCountDownView.start {
            Toast.makeText(this@CountDownViewActivity, "倒计时已经完成，做点什么事吧", Toast.LENGTH_SHORT).show()
        }
    }
}
