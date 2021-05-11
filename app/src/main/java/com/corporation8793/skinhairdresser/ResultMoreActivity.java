package com.corporation8793.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResultMoreActivity extends AppCompatActivity {
    //피부 결과 자세히 화면

    Button finish_btn, recommend_btn;
    ProgressBar oil_progress, moisture_progress, sebum_secretion_progress, wrinkle_progress, pore_progress, sensitivity_progress, skin_tone_progress, pigmentation_progress;
    String [] progress_values = {"93 55 90 47 80 90 87 89", "83 50 90 53 80 50 55 52", "57 79 52 50 51 60 49 62", "20 30 35 50 52 70 48 55"};

    ProgressBar [] progress = new ProgressBar[8];
    TextView [] percentage = new TextView[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_more);

        finish_btn = findViewById(R.id.finish_btn);
        recommend_btn = findViewById(R.id.recommend_btn);

        progress[0] = findViewById(R.id.oil_progress);
        progress[1] = findViewById(R.id.moisture_progress);
        progress[2] = findViewById(R.id.sebum_secretion_progress);
        progress[3] = findViewById(R.id.wrinkle_progress);
        progress[4] = findViewById(R.id.pore_progress);
        progress[5] = findViewById(R.id.sensitivity_progress);
        progress[6] = findViewById(R.id.skin_tone_progress);
        progress[7] = findViewById(R.id.pigmentation_progress);

        percentage[0] = findViewById(R.id.oil_text);
        percentage[1] = findViewById(R.id.moisture_text);
        percentage[2] = findViewById(R.id.sebum_secretion_text);
        percentage[3] = findViewById(R.id.wrinkle_text);
        percentage[4] = findViewById(R.id.pore_text);
        percentage[5] = findViewById(R.id.sensitivity_text);
        percentage[6] = findViewById(R.id.skin_tone_text);
        percentage[7] = findViewById(R.id.pigmentation_text);



        Intent intent = getIntent();
        String [] values = progress_values[intent.getIntExtra("value",0)].split(" ");

        for (int i=0; i <8; i++){
            Log.e("list",Integer.parseInt(values[i])+"");
            progress[i].setProgress(Integer.parseInt(values[i]));
            percentage[i].setText(values[i]+"%");
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
}