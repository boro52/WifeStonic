package com.example.wifestonic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AboutActivity extends AppCompatActivity {

    //Mamy dodany plik wsb_logo.png ktory wystwietlamy na ekranie
    //Jest dodany w activity_about.xml
    //Activity stworzone to użycia menu, dodany powrót do parenta przez dodanie do manifestu
    //android:parentActivityName=".MainActivity">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}