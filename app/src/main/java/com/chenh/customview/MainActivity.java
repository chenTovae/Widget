package com.chenh.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountDownView mCountDownView = (CountDownView) findViewById(R.id.count_view);
        mCountDownView.start(new CountDownView.OnCountDownCallback() {
            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "倒计时已经完成，做点什么事吧", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
