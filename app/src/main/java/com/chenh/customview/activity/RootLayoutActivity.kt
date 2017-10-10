package com.chenh.customview.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.chenh.customview.R
import kotlinx.android.synthetic.main.activity_root_layout.*

class RootLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root_layout)
        mRootLayout.showEmpty()
//        mRootLayout.showError()
//        mRootLayout.showNetwork()
    }
}
