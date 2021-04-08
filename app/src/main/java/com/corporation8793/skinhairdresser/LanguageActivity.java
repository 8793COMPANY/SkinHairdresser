package com.corporation8793.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {
    //언어 설정 페이지

    Button korean,english,chinese,japanese;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        korean = findViewById(R.id.korean);
        english = findViewById(R.id.english);
        chinese = findViewById(R.id.chinese);
        japanese = findViewById(R.id.japanese);

        korean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage(Locale.KOREA);
                Intent intent = new Intent(LanguageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
//                Locale locale = Locale.KOREA;
//
//                Locale.setDefault(locale);
//
//                Configuration config = new Configuration();
//
//                config.locale = locale;
//
//                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage(Locale.US);
                Intent intent = new Intent(LanguageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
//                Locale locale = Locale.US;
//
//                Locale.setDefault(locale);
//
//                Configuration config = new Configuration();
//
//                config.locale = locale;
//
//                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            }
        });

        chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage(Locale.CHINA);
                Intent intent = new Intent(LanguageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
//                Locale locale = Locale.CHINA;
//
//                Locale.setDefault(locale);
//
//                Configuration config = new Configuration();
//
//                config.locale = locale;
//
//                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            }
        });

        japanese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage(Locale.JAPAN);
                Intent intent = new Intent(LanguageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
//                Locale locale = Locale.JAPAN;
//
//                Locale.setDefault(locale);
//
//                Configuration config = new Configuration();
//
//                config.locale = locale;
//
//                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            }
        });

    }

    void setLanguage(Locale locale){

        Locale.setDefault(locale);

        Configuration config = new Configuration();

        config.locale = locale;

        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}