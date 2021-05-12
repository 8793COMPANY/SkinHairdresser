package com.corporation8793.skinhairdresser;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.Arrays;

public class ResultMoreActivity extends AppCompatActivity {
    //피부 결과 자세히 화면

    Button finish_btn, recommend_btn;
    ProgressBar oil_progress, moisture_progress, sebum_secretion_progress, wrinkle_progress, pore_progress, sensitivity_progress, skin_tone_progress, pigmentation_progress;
    String [] progress_values = {"100 55 90 47 80 90 87 89", "100 50 90 53 80 50 55 52", "57 79 52 50 51 60 49 62", "20 30 35 50 52 70 48 55"};

    NumberProgressBar [] progress = new NumberProgressBar[7];
    TextView [] percentage = new TextView[8];
    NumberProgressBar numberProgressBar;
    String [] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_more);

        Intent intent = getIntent();
        try {
            values = intent.getStringExtra("value").split(" ");
        }catch (NullPointerException e){
            values = "100 55 90 47 80 90 87 89".split(" ");
        }

        if (values.length ==0)
            Log.e("length","0");

        finish_btn = findViewById(R.id.finish_btn);
        recommend_btn = findViewById(R.id.recommend_btn);
        numberProgressBar =findViewById(R.id.oil_progress);


        progress[0] = findViewById(R.id.oil_progress);
        progress[1] = findViewById(R.id.moisture_progress);
        progress[2] = findViewById(R.id.sebum_secretion_progress);
        progress[3] = findViewById(R.id.wrinkle_progress);
        progress[4] = findViewById(R.id.pore_progress);
        progress[5] = findViewById(R.id.skin_tone_progress);
        progress[6] = findViewById(R.id.pigmentation_progress);




//        Intent intent = getIntent();
//        String [] values = progress_values[intent.getIntExtra("value",0)].split(" ");

        int score = 0;
        for (int i=0; i <7; i++){
            Log.e("list",values[i]+"");
            score = Integer.parseInt(values[i]) *15;
            progress[i].setProgress(score);
            if (score <30){
                progress[i].setReachedBarColor(Color.parseColor("#dfc2ac"));
            }else if(score >=30 && score <60){
                progress[i].setReachedBarColor(Color.parseColor("#bc8b6c"));
            }else{
                progress[i].setReachedBarColor(Color.parseColor("#ccbc8b6c"));
            }
        }

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recommend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultMoreActivity.this, RecommendWebView.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setProgressBarColor(ProgressBar progressBar, int newColor){
        LayerDrawable ld =(LayerDrawable) progressBar.getProgressDrawable();
        ClipDrawable dl = (ClipDrawable) ld.findDrawableByLayerId(R.id.progress);
        dl.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
    }
}