package com.victoria.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class ConfirmActivity extends AppCompatActivity {
    //앰플 잔량 확인
    ProgressBar aqua_percentage;
    Button finish_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        aqua_percentage = findViewById(R.id.aqua_percentage);
        finish_btn = findViewById(R.id.finish_btn);

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        aqua_percentage.setMax(100);
        aqua_percentage.setProgress(50);
    }
}