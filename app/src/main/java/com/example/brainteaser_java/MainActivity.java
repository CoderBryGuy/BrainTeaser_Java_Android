package com.example.brainteaser_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    public void startPuzzle(View view){
        Intent gameActivity = new Intent(MainActivity.this, PuzzleActivity.class);
        startActivity(gameActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}