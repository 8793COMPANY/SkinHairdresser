package com.corporation8793.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class RecommendActivity extends AppCompatActivity {
    //개인 맞춤 성분 추천
    Button test,test2;
    LinearLayout top_linear,middle_linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        int height = size.y;

        top_linear = findViewById(R.id.top_linear);
        middle_linear = findViewById(R.id.middle_linear);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) top_linear.getLayoutParams();
        params.height = (int) ((height/ 1280.0) * 120);

        top_linear.setLayoutParams(params);


        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) middle_linear.getLayoutParams();
        params2.height = (int) ((height/ 1280.0) * 120);
        params2.topMargin = (int) ((height/ 1280.0) * 100);

        middle_linear.setLayoutParams(params2);

//        top_linear.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0, (int) ((height/ 1280.0) * 120)));

//        test = findViewById(R.id.test);
//        test2 = findViewById(R.id.test2);
//        test.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,0, (int) ((height/ 1280.0) * 36)));
//        test2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,0, (int) ((height/ 1280.0) * 36)));

    }
}