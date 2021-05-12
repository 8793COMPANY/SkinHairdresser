package com.corporation8793.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.corporation8793.skinhairdresser.Room.AppDatabase;
import com.corporation8793.skinhairdresser.Room.Measurement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class ResultActivity extends AppCompatActivity {
    // -> 진단 결과 화면
    // 글로벌
    Button let_out_btn, back_btn, result_more_btn;
    String [] advice_text = {};
    RadarChart real_result_graph;

    // 측정값 저장 배열
    int[] values = new int[]{
         0, 0, 0, 0, 0, 0, 0
    };

    AppDatabase db;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        let_out_btn = findViewById(R.id.let_out_btn);
        back_btn = findViewById(R.id.back_btn);
        result_more_btn = findViewById(R.id.result_more_btn);
        real_result_graph = findViewById(R.id.real_result_graph);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "DB").allowMainThreadQueries().build();
        calendar = Calendar.getInstance();


        // 측정값 초기화
        Random random = new Random();
        int j = 0;
        for (int i : values) {
            values[j] = random.nextInt(5) + 1;
            Log.e("values",values[j]+"");
            j++;

        }

        // 준비된 DB 로우
        Measurement measurement = new Measurement(
                // Y-M-D
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),

                // pg
                values[0],
                // moisture
                values[1],
                // hole
                values[2],
                // tone
                values[3],
                // color_washing
                values[4],
                // fold
                values[5],
                // oil
                values[6]
        );

        // 오늘 날짜, DB (오늘)날짜 비교해서 있으면 DB UPDATE
        if (db.measurementDao().findByYmd(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)) != null) {

            Log.e("DB", "-- 머리 업데이트 확인? --");
            Log.e("DB", "cid : " + measurement.cid + " , " + "year : " + measurement.year + " , " + "month : " + measurement.month + " , " + "day : " + measurement.day + " , "
                    + "pg : " + measurement.pg + " , " + "moisture : " + measurement.moisture + " , " + "hole : " + measurement.hole + " , " + "tone : " + measurement.tone + " , "
                    + "color washing : " + measurement.color_washing + " , " + "fold : " + measurement.fold + " , " + "oil : " + measurement.oil);

            Measurement LM = db.measurementDao().findByYmd(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH));

            LM.setPg(values[0]);
            LM.setMoisture(values[1]);
            LM.setHole(values[2]);
            LM.setTone(values[3]);
            LM.setColor_washing(values[4]);
            LM.setFold(values[5]);
            LM.setOil(values[6]);

            db.measurementDao().update(LM);
        } else {
            // 없으면 DB INSERT
            db.measurementDao().insertAll(measurement);
        }

        // 레이더 그래프 초기화 (RadarEntry - 밸류 어레이)
        // RadarEntry - 데이터 초기화
        List<RadarEntry> entries = new ArrayList<>();
        entries.add(new RadarEntry(values[0], 0));
        entries.add(new RadarEntry(values[1], 0));
        entries.add(new RadarEntry(values[2], 0));
        entries.add(new RadarEntry(values[3], 0));
        entries.add(new RadarEntry(values[4], 0));
        entries.add(new RadarEntry(values[5], 0));
        entries.add(new RadarEntry(values[6], 0));

        // 레이더 그래프 초기화 (RadarDataSet - 밸류 컨트롤러)
        // RadarDataSet - 데이터 스타일링
        RadarDataSet radarDataSet = new RadarDataSet(entries, "");
        radarDataSet.setDrawValues(false);
        radarDataSet.setColor(Color.parseColor("#bc8b6c"));
        radarDataSet.setLineWidth(2.6f);
        radarDataSet.setDrawFilled(true);
        radarDataSet.setFillColor(Color.parseColor("#bc8b6c"));
        radarDataSet.setFillAlpha(153);

        // 레이더 그래프 초기화 (RadarData - 데이터 컨테이너)
        // * RadarData - 데이터 라벨링 Start
        RadarData radarData = new RadarData(radarDataSet);
        String[] strings = new String[]{
                "피지분비", "수분", "모공", "피부톤", "색소침착", "주름", "유분", ""
        };
        ValueFormatter valueFormatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return strings[(int) value];
            }
        };
        // * RadarData - 데이터 라벨링 End

        // * 레이더 그래프 스타일링 Start
        // X 좌표
        XAxis xAxis = real_result_graph.getXAxis();
        xAxis.setValueFormatter(valueFormatter);
        xAxis.setAxisMaximum(5);
        xAxis.setAxisMinimum(1);
        xAxis.setDrawLabels(false);
        // Y 좌표
        YAxis yAxis = real_result_graph.getYAxis();
        yAxis.setDrawLabels(false);
        // 설명
        Description description = real_result_graph.getDescription();
        description.setEnabled(false);
        // 범례
        Legend legend = real_result_graph.getLegend();
        legend.setEnabled(false);
        // 라인, 투명도 등
        real_result_graph.setWebColor(Color.parseColor("#FFFFFF"));
        real_result_graph.setWebColorInner(Color.parseColor("#FFFFFF"));
        real_result_graph.setAlpha(1);
        real_result_graph.setWebLineWidth(2.1f);
        real_result_graph.setWebLineWidthInner(2.1f);
        real_result_graph.setRotationEnabled(false);
        // * 레이더 그래프 스타일링 End

        // 레이더 그래프 데이터 설정
        real_result_graph.setData(radarData);
        real_result_graph.invalidate();

        // DB 로그 체크
        List<Measurement> all = db.measurementDao().getAll();
        Log.e("DB", "-- 머리에 뭐가 들었을꼬? --");
        for (Measurement m : all) {
            Log.e("DB", "cid : " + m.cid + " , " + "year : " + m.year + " , " + "month : " + m.month + " , " + "day : " + m.day + " , "
                    + "pg : " + m.pg + " , " + "moisture : " + m.moisture + " , " + "hole : " + m.hole + " , " + "tone : " + m.tone + " , "
                    + "color washing : " + m.color_washing + " , " + "fold : " + m.fold + " , " + "oil : " + m.oil);
        }


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
                intent.putExtra("value",values[6]+" "+values[1]+" "+values[0]+" "+values[5]+" "+values[2]+" "+values[3]+" "+values[4]);
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