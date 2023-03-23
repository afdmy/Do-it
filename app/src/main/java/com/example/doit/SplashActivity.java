package com.example.doit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示View
        setContentView(R.layout.activity_splash);
        //隐藏标题栏
        getSupportActionBar().hide();

        final Intent i = new Intent(SplashActivity.this,MainActivity.class);
        //延时执行跳转
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        },2000);
    }
}