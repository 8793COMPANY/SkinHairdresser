package com.victoria.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.security.acl.Group;

public class JoinActivity extends AppCompatActivity {

    //회원가입

    Button camera_btn;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Display display = getWindowManager().getDefaultDisplay();  // in Activity
        /* getActivity().getWindowManager().getDefaultDisplay() */ // in Fragment
        Point size = new Point();
        display.getRealSize(size); // or getSize(size)
        int width = size.x;
        int height = size.y;

        camera_btn = findViewById(R.id.camera_btn);
        linearLayout = findViewById(R.id.camera_linear);

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
}