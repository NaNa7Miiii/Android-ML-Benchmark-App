package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FunctionActivity extends AppCompatActivity {

    Button imageClassificationBtn, backBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        imageClassificationBtn = findViewById(R.id.imgClassification);
        backBtn = findViewById(R.id.backButton);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to the FunctionActivity
                Intent intent = new Intent(FunctionActivity.this,  MainActivity.class);
                startActivity(intent);
            }
        });

        imageClassificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // jump to model selection
                Intent intent = new Intent(FunctionActivity.this, SelectModelActivity.class);
                startActivity(intent);
            }
        });
    }
}