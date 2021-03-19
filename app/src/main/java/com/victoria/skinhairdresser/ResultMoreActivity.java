package com.victoria.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import javax.xml.transform.Result;

public class ResultMoreActivity extends AppCompatActivity {
    //피부 결과 자세히 화면

    Button finish_btn, recommend_btn;
    ProgressBar oil_progress, moisture_progress,sebum_secretion_progress, wrinkle_progress, pore_progress, sensitivity_progress, skin_tone_progress, pigmentation_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_more);

        finish_btn = findViewById(R.id.finish_btn);
        recommend_btn = findViewById(R.id.recommend_btn);

        oil_progress = findViewById(R.id.oil_progress);
        moisture_progress = findViewById(R.id.moisture_progress);
        sebum_secretion_progress = findViewById(R.id.sebum_secretion_progress);
        wrinkle_progress = findViewById(R.id.wrinkle_progress);
        pore_progress = findViewById(R.id.pore_progress);
        sensitivity_progress = findViewById(R.id.sensitivity_progress);
        skin_tone_progress = findViewById(R.id.skin_tone_progress);
        pigmentation_progress = findViewById(R.id.pigmentation_progress);

        oil_progress.setProgress(83);
        moisture_progress.setProgress(65);
        sebum_secretion_progress.setProgress(75);
        wrinkle_progress.setProgress(70);
        pore_progress.setProgress(70);
        sensitivity_progress.setProgress(70);
        skin_tone_progress.setProgress(67);
        pigmentation_progress.setProgress(80);



        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recommend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultMoreActivity.this, RecommendActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}