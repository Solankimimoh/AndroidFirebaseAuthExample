package com.lj.ljengineeringcollege;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashScreenActivity extends AppCompatActivity {

    private Handler handler;
    final String TAG = SplashScreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler = new Handler();
        handler.postDelayed(runnable, 2000);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent gotoLogin = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(gotoLogin);
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
        handler.removeCallbacks(runnable);
    }
}
