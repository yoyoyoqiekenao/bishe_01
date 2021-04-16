package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean isLogin = false;

    private Runnable mRunable = new Runnable() {
        @Override
        public void run() {
            jump();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImmersionBar.with(this).init();
        SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
        isLogin = sp.getBoolean("isLogin", false);

        mHandler.postDelayed(mRunable, 3000);
    }

    private void jump() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtra("isLogin", isLogin);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
