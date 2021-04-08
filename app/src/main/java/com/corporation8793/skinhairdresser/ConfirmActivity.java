package com.corporation8793.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ConfirmActivity extends AppCompatActivity {
    //앰플 잔량 확인
    ProgressBar component1,component2,component3,component4,component5,component6;
    Button finish_btn, start_btn, order_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        component1 = findViewById(R.id.component_progress1);
        component2 = findViewById(R.id.component_progress2);
        component3 = findViewById(R.id.component_progress3);
        component4 = findViewById(R.id.component_progress4);
        component5 = findViewById(R.id.component_progress5);
        component6 = findViewById(R.id.component_progress6);


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

        component1.setProgress(80);
        component2.setProgress(50);
        component3.setProgress(80);
        component4.setProgress(40);
        component5.setProgress(100);
        component6.setProgress(80);


    }
}