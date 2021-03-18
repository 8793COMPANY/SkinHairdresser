package com.victoria.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    // 내 정보 화면
    // 글로벌
    AppCompatButton back_btn;
    LinearLayout setting_edit_info;
    View setting_go_start, setting_go_manage;
    Switch setting_letOut, setting_start, setting_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // 로컬
        back_btn = findViewById(R.id.back_btn);
        setting_edit_info = findViewById(R.id.setting_edit_info);
        setting_go_start = findViewById(R.id.setting_go_start);
        setting_go_manage = findViewById(R.id.setting_go_manage);
        setting_letOut = findViewById(R.id.setting_letOut);
        setting_start = findViewById(R.id.setting_start);
        setting_login = findViewById(R.id.setting_login);


        // 스위치 제어
        setting_letOut.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // ON
                Toast.makeText(this, "토출기 ON", Toast.LENGTH_SHORT).show();
            } else {
                // OFF
                Toast.makeText(this, "토출기 OFF", Toast.LENGTH_SHORT).show();
            }
        });

        setting_start.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // ON
                Toast.makeText(this, "측정기 ON", Toast.LENGTH_SHORT).show();
            } else {
                // OFF
                Toast.makeText(this, "측정기 OFF", Toast.LENGTH_SHORT).show();
            }
        });

        setting_login.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // ON
                Toast.makeText(this, "자동 로그인 ON", Toast.LENGTH_SHORT).show();
            } else {
                // OFF
                Toast.makeText(this, "자동 로그인 OFF", Toast.LENGTH_SHORT).show();
            }
        });


        // 인텐트 이동
        back_btn.setOnClickListener(v -> {
            finish();
        });

        setting_edit_info.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
            startActivity(intent);
            finish();
        });

        setting_go_start.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
            startActivity(intent);
            finish();
        });

        setting_go_manage.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ManageActivity.class);
            startActivity(intent);
            finish();
        });
    }
}