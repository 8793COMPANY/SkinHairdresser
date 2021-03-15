package com.victoria.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.storage.OnObbStateChangeListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    //로그인

    Button login_btn,join_btn;
    EditText model_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login_btn = findViewById(R.id.login_btn);
        join_btn = findViewById(R.id.join_btn);
        model_number = findViewById(R.id.model_num);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model_number.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"모델번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}