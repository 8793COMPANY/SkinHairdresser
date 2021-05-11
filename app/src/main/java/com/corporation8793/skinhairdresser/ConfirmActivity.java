package com.corporation8793.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmActivity extends AppCompatActivity {
    //앰플 잔량 확인
    ProgressBar component1,component2,component3,component4,component5,component6;
    ProgressBar [] progress = new ProgressBar[8];
    Button finish_btn, start_btn, order_btn;
    TextView component_percentage1, component_percentage2, component_percentage3, component_percentage4, component_percentage5, component_percentage6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        SharedPreferences sharedPreferences = getSharedPreferences("appData",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

//        component1 = findViewById(R.id.component_progress1);
//        component2 = findViewById(R.id.component_progress2);
//        component3 = findViewById(R.id.component_progress3);
//        component4 = findViewById(R.id.component_progress4);
//        component5 = findViewById(R.id.component_progress5);
//        component6 = findViewById(R.id.component_progress6);


        progress[0] = findViewById(R.id.component_progress1);
        progress[1] = findViewById(R.id.component_progress2);
        progress[2] = findViewById(R.id.component_progress3);
        progress[3] = findViewById(R.id.component_progress4);
        progress[4] = findViewById(R.id.component_progress5);
        progress[5] = findViewById(R.id.component_progress6);


        component_percentage1 = findViewById(R.id.component_percentage1);
        component_percentage2 = findViewById(R.id.component_percentage2);
        component_percentage3 = findViewById(R.id.component_percentage3);
        component_percentage4 = findViewById(R.id.component_percentage4);
        component_percentage5 = findViewById(R.id.component_percentage5);
        component_percentage6 = findViewById(R.id.component_percentage6);


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

//        progress[0].setProgress((sharedPreferences.getInt("A",20) <= 0) ? 0: sharedPreferences.getInt("A",20));
//        progress[1].setProgress((sharedPreferences.getInt("B",20) <= 0) ? 0: sharedPreferences.getInt("B",20));
//        progress[2].setProgress((sharedPreferences.getInt("C",20) <= 0) ? 0: sharedPreferences.getInt("C",20));
//        progress[3].setProgress((sharedPreferences.getInt("D",20) <= 0) ? 0: sharedPreferences.getInt("D",20));
//        progress[4].setProgress((sharedPreferences.getInt("E",20) <= 0) ? 0: sharedPreferences.getInt("E",20));
//        progress[5].setProgress((sharedPreferences.getInt("F",20) <= 0) ? 0: sharedPreferences.getInt("F",20));


        progress[0].setProgress(5);
        progress[1].setProgress(13);
        progress[2].setProgress(9);
        progress[3].setProgress((sharedPreferences.getInt("D",20) <= 0) ? 0: sharedPreferences.getInt("D",20));
        progress[4].setProgress((sharedPreferences.getInt("E",20) <= 0) ? 0: sharedPreferences.getInt("E",20));
        progress[5].setProgress((sharedPreferences.getInt("F",20) <= 0) ? 0: sharedPreferences.getInt("F",20));


        component_percentage1.setText(progress[0].getProgress()*5+"%");
        component_percentage2.setText(progress[1].getProgress()*5+"%");
        component_percentage3.setText(progress[2].getProgress()*5+"%");
        component_percentage4.setText(progress[3].getProgress()*5+"%");
        component_percentage5.setText(progress[4].getProgress()*5+"%");
        component_percentage6.setText(progress[5].getProgress()*5+"%");

        for (int i=0; i<6; i++){
            Log.e("progress percentage", progress[i].getProgress()+"");
            if (progress[i].getProgress() <6){
                setProgressBarColor(progress[i],R.color.main_brown);
            }else if(progress[i].getProgress() <12){
                setProgressBarColor(progress[i],R.color.middle_times);
            }else if (progress[i].getProgress() > 11 &&progress[i].getProgress()<=20){
                setProgressBarColor(progress[i],R.color.above_twelve_times);
            }
        }

    }

    public void setProgressBarColor(ProgressBar progressBar, int newColor){
        LayerDrawable ld =(LayerDrawable) progressBar.getProgressDrawable();
        ClipDrawable dl = (ClipDrawable) ld.findDrawableByLayerId(R.id.vertical_progress);
        dl.setColorFilter(getResources().getColor(newColor), PorterDuff.Mode.SRC_IN);
    }
}