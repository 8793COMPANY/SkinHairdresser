package com.corporation8793.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.InputStream;

public class JoinActivity extends AppCompatActivity implements TextWatcher {

    //회원가입

    Button camera_btn, woman_btn,man_btn,confirm_btn,join_btn,finish_btn;
    LinearLayout linearLayout,augment_view,reduce_view,top_bar;
    EditText first_name,last_name,birth,address,detail,model_number;
    boolean confirm = false;
    private static final int REQUEST_CODE = 0;
    ShapeableImageView user_image;
    TextView detail_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        SharedPreferences sharedPreferences = getSharedPreferences("appData",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        int height = size.y;

        user_image = findViewById(R.id.user_image);
        camera_btn = findViewById(R.id.camera_btn);
        linearLayout = findViewById(R.id.camera_linear);
        woman_btn = findViewById(R.id.woman_btn);
        man_btn = findViewById(R.id.man_btn);
        confirm_btn = findViewById(R.id.confirm_btn);
        join_btn = findViewById(R.id.join_btn);
        detail_text = findViewById(R.id.detail_text);
        finish_btn = findViewById(R.id.finish_btn);

        first_name = findViewById(R.id.first_name_input);
        last_name = findViewById(R.id.last_name_input);
        birth = findViewById(R.id.birth_input);
        address = findViewById(R.id.address_input);
        detail = findViewById(R.id.address_detail_input);
        model_number = findViewById(R.id.model_num_input);

        top_bar = findViewById(R.id.top_bar);
        augment_view = findViewById(R.id.augment_view);
        reduce_view = findViewById(R.id.reduce_view);

        first_name.addTextChangedListener(this);
        last_name.addTextChangedListener(this);
        birth.addTextChangedListener(this);
        address.addTextChangedListener(this);
        detail.addTextChangedListener(this);


        if (getIntent().getStringExtra("detail").equals("edit")){
            top_bar.setVisibility(View.VISIBLE);
            detail_text.setText("");
            augment_view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,0, (int) ((height/ 1280.0) * 36)));
            reduce_view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,0, (int) ((height/ 1280.0) * 110)));

            first_name.setText(sharedPreferences.getString("first_name","혜진"));
            last_name.setText(sharedPreferences.getString("last_name","백"));
            birth.setText(sharedPreferences.getString("birth","2000.03.21"));
            address.setText(sharedPreferences.getString("address","00시 00구 00동"));
            detail.setText(sharedPreferences.getString("detail","00아파트 00동 00호"));
            model_number.setText(sharedPreferences.getString("model_num","AA1234"));

            join_btn.setBackgroundResource(R.drawable.brown_btn_img);
            join_btn.setText("수정하기");
            join_btn.setTextColor(getResources().getColor(R.color.white));
            join_btn.setEnabled(true);

            if (sharedPreferences.getInt("sex",0) == 0){
                woman_btn.setBackgroundResource(R.drawable.woman_btn_on);
                woman_btn.setTextColor(getResources().getColor(R.color.white));
            }else{
                man_btn.setBackgroundColor(getResources().getColor(R.color.main_brown));
                man_btn.setTextColor(getResources().getColor(R.color.white));
            }
        }


        woman_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                woman_btn.setBackgroundResource(R.drawable.woman_btn_on);
                woman_btn.setTextColor(getResources().getColor(R.color.white));
                man_btn.setBackgroundColor(getResources().getColor(R.color.transparent));
                man_btn.setTextColor(getResources().getColor(R.color.main_brown));
                editor.putInt("sex",0);
            }
        });

        man_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                woman_btn.setBackgroundColor(getResources().getColor(R.color.transparent));
                woman_btn.setTextColor(getResources().getColor(R.color.main_brown));
                man_btn.setBackgroundColor(getResources().getColor(R.color.main_brown));
                man_btn.setTextColor(getResources().getColor(R.color.white));
                editor.putInt("sex",1);
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
                        join_btn.setEnabled(true);
                    }
                }


            }
        });

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("last_name",last_name.getText().toString());
                editor.putString("first_name",first_name.getText().toString());
                editor.putString("address",address.getText().toString());
                editor.putString("detail",detail.getText().toString());
                editor.putString("model_num",model_number.getText().toString());
                editor.commit();
                finish();
            }
        });

        finish_btn.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
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
            join_btn.setEnabled(true);
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    BitmapDrawable ob = new BitmapDrawable(getResources(), img);
                    user_image.setBackground(ob);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}