package com.victoria.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.security.acl.Group;

public class JoinActivity extends AppCompatActivity implements TextWatcher {

    //회원가입

    Button camera_btn, woman_btn,man_btn,confirm_btn,join_btn;
    LinearLayout linearLayout;
    EditText first_name,last_name,birth,address,detail,model_number;
    boolean confirm = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        int height = size.y;

        camera_btn = findViewById(R.id.camera_btn);
        linearLayout = findViewById(R.id.camera_linear);
        woman_btn = findViewById(R.id.woman_btn);
        man_btn = findViewById(R.id.man_btn);
        confirm_btn = findViewById(R.id.confirm_btn);
        join_btn = findViewById(R.id.join_btn);

        first_name = findViewById(R.id.first_name_input);
        last_name = findViewById(R.id.last_name_input);
        birth = findViewById(R.id.birth_input);
        address = findViewById(R.id.address_input);
        detail = findViewById(R.id.address_detail_input);
        model_number = findViewById(R.id.model_num_input);

        first_name.addTextChangedListener(this);
        last_name.addTextChangedListener(this);
        birth.addTextChangedListener(this);
        address.addTextChangedListener(this);
        detail.addTextChangedListener(this);


        woman_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                woman_btn.setBackgroundResource(R.drawable.woman_btn_on);
                woman_btn.setTextColor(getResources().getColor(R.color.white));
                man_btn.setBackgroundColor(getResources().getColor(R.color.transparent));
                man_btn.setTextColor(getResources().getColor(R.color.main_brown));
            }
        });

        man_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                woman_btn.setBackgroundColor(getResources().getColor(R.color.transparent));
                woman_btn.setTextColor(getResources().getColor(R.color.main_brown));
                man_btn.setBackgroundColor(getResources().getColor(R.color.main_brown));
                man_btn.setTextColor(getResources().getColor(R.color.white));
            }
        });
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model_number.getText().toString().trim().equals(""))
                    Toast.makeText(getApplicationContext(),"모델번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(getApplicationContext(),"모델번호가 확인되었습니다.",Toast.LENGTH_SHORT).show();
                    confirm = true;

                    if (textCheck(first_name) && textCheck(last_name) && textCheck(birth) && textCheck(address) && textCheck(detail) && confirm){
                        join_btn.setBackgroundResource(R.drawable.brown_btn_img);
                        join_btn.setTextColor(getResources().getColor(R.color.white));
                    }
                }


            }
        });

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) linearLayout.getLayoutParams();
        params.height = (int) ((height/ 1280.0) * 70);

        linearLayout.setLayoutParams(params);




        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("click","click");
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.e("??","ddd");
        if (textCheck(first_name) && textCheck(last_name) && textCheck(birth) && textCheck(address) && textCheck(detail) && confirm){
            join_btn.setBackgroundResource(R.drawable.brown_btn_img);
            join_btn.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    boolean textCheck(EditText editText){
        if (editText.getText().toString().trim().equals(""))
            return false;
        else
            return true;


    }
}