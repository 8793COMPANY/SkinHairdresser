package com.victoria.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ConfirmActivity extends AppCompatActivity {
    //앰플 잔량 확인
    ProgressBar aqua_percentage;
    Button finish_btn, start_btn, order_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        aqua_percentage = findViewById(R.id.aqua_percentage);
        finish_btn = findViewById(R.id.finish_btn);
        order_btn = findViewById(R.id.order_btn);
        start_btn = findViewById(R.id.start_btn);

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"주문하기",Toast.LENGTH_SHORT).show();
            }
        });

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        aqua_percentage.setMax(100);
        aqua_percentage.setProgress(50);
    }
}