package com.corporation8793.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class IntroActivity extends AppCompatActivity {
    //인트로
    int count = 0;
    ProgressBar progressBar;
    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), LanguageActivity.class);
            startActivity(intent);
            finish();
        }
    };

    Runnable progress_start = new Runnable() {
        @Override
        public void run() {
            if (count == 300){
                handler.removeCallbacksAndMessages(null);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }else{
                count++;
                progressBar.setProgress(count);
                handler.postDelayed(progress_start,10);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        progressBar = findViewById(R.id.progress);
        progressBar.setMax(300);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        handler.postDelayed(r, 3000); // 4초 뒤에 Runnable 객체 수행
        handler.post(progress_start);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(r); // 예약 취소
    }

}