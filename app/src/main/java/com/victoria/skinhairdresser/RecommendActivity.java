package com.victoria.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class RecommendActivity extends AppCompatActivity {
    //개인 맞춤 성분 추천
    Button test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        test = findViewById(R.id.test);

    }
}