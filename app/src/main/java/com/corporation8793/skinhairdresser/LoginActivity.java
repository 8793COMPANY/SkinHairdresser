package com.corporation8793.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    //로그인

    Button login_btn,join_btn,auto_login_btn;
    EditText model_number;

    boolean auto_login_check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = findViewById(R.id.login_btn);
        join_btn = findViewById(R.id.join_btn);
        auto_login_btn = findViewById(R.id.auto_login_btn);
        model_number = findViewById(R.id.model_num);

        SharedPreferences sharedPreferences = getSharedPreferences("appData",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String model_num = sharedPreferences.getString("model_num","?");


        if (sharedPreferences.getBoolean("auto_login",false)){
            if (!model_num.equals("?"))
                model_number.setText(model_num);
            auto_login_btn.setBackgroundResource(R.drawable.auto_login_btn_on);
            auto_login_check = true;
        }



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model_number.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"모델번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    if (model_num.equals("?")){
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        if (model_number.getText().toString().trim().equals(model_num)) {
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"모델번호가 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });


        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                intent.putExtra("detail","join");
                startActivity(intent);
            }
        });



        View parentView = findViewById(R.id.login_linear);

        parentView.post(new Runnable() {
            // Post in the parent's message queue to make sure the parent
            // lays out its children before you call getHitRect()
            @Override
            public void run() {
                // The bounds for the delegate view (an ImageButton
                // in this example)
                Rect delegateArea = new Rect();
                auto_login_btn.setEnabled(true);
                auto_login_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (auto_login_check){
                            auto_login_btn.setBackgroundResource(R.drawable.auto_login_btn_off);
                            auto_login_check = false;
                            editor.putBoolean("auto_login",false);
                        }else{
                            auto_login_btn.setBackgroundResource(R.drawable.auto_login_btn_on);
                            auto_login_check = true;
                            editor.putBoolean("auto_login",true);
                        }
                    }
                });

                // The hit rectangle for the ImageButton
                auto_login_btn.getHitRect(delegateArea);

                // Extend the touch area of the ImageButton beyond its bounds
                // on the right and bottom.
                delegateArea.left += 100;
                delegateArea.right += 100;
                delegateArea.bottom += 14;
                delegateArea.top += 14;

                // Instantiate a TouchDelegate.
                // "delegateArea" is the bounds in local coordinates of
                // the containing view to be mapped to the delegate view.
                // "myButton" is the child view that should receive motion
                // events.
                TouchDelegate touchDelegate = new TouchDelegate(delegateArea,
                        auto_login_btn);

                // Sets the TouchDelegate on the parent view, such that touches
                // within the touch delegate bounds are routed to the child.
                if (View.class.isInstance(auto_login_btn.getParent())) {
                    ((View) auto_login_btn.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }
}