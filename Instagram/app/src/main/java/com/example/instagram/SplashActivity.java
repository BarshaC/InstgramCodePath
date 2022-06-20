package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    boolean handler = new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }, 3000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

}
