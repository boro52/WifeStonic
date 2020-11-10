package com.example.wifestonic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class StartupScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_screen);

        //Tworzymy odowłanie do MainActivity
        Intent i = new Intent(StartupScreenActivity.this, MainActivity.class);

        //Po minięciu delay'u, uruchomimy nowe activity stworzone wyzej i zakonczymy StartupScreenActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}