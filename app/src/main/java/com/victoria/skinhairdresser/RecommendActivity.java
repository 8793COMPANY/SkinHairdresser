package com.victoria.skinhairdresser;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class RecommendActivity extends AppCompatActivity {
    //개인 맞춤 성분 추천
    Button finish_btn;
    LinearLayout top_linear,middle_linear;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        int height = size.y;


        finish_btn = findViewById(R.id.finish_btn);
        top_linear = findViewById(R.id.top_linear);
        middle_linear = findViewById(R.id.middle_linear);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) top_linear.getLayoutParams();
        params.height = (int) ((height/ 1280.0) * 120);

        top_linear.setLayoutParams(params);


        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) middle_linear.getLayoutParams();
        params2.height = (int) ((height/ 1280.0) * 120);
        params2.topMargin = (int) ((height/ 1280.0) * 100);

        middle_linear.setLayoutParams(params2);

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}