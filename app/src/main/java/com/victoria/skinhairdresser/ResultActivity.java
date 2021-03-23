package com.victoria.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.victoria.skinhairdresser.Room.AppDatabase;
import com.victoria.skinhairdresser.Room.Measurement;

import java.util.Calendar;
import java.util.Random;

import javax.xml.transform.Result;

public class ResultActivity extends AppCompatActivity {
    //진단 결과 화면
    Button let_out_btn, back_btn, result_more_btn;
    ImageView result_graph;
    int [] graph_img = {R.drawable.result_img1,R.drawable.result_img2,R.drawable.result_img3,R.drawable.result_img4};
    String [] advice_text = {};

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        let_out_btn = findViewById(R.id.let_out_btn);
        back_btn = findViewById(R.id.back_btn);
        result_more_btn = findViewById(R.id.result_more_btn);
        result_graph = findViewById(R.id.result_graph);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "DB").build();

        Random random = new Random();
        int randomValue = random.nextInt(4);
        Log.e("randomValue",randomValue+"");
        result_graph.setBackgroundResource(graph_img[randomValue]);

        Calendar calendar = Calendar.getInstance();

        Measurement measurement = new Measurement(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),

                // pg
                random.nextInt(4),
                // moisture
                random.nextInt(4),
                // hole
                random.nextInt(4),
                // tone
                random.nextInt(4),
                // color_washing
                random.nextInt(4),
                // sensitivity
                random.nextInt(4),
                // fold
                random.nextInt(4),
                // oil
                random.nextInt(4)
        );

        db.measurementDao().insertAll(measurement);

        let_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, LetOutActivity.class);
                startActivity(intent);
                finish();
            }
        });

        result_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, ResultMoreActivity.class);
                intent.putExtra("value",randomValue);
                startActivity(intent);
                finish();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}