package com.victoria.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.victoria.skinhairdresser.Room.AppDatabase;
import com.victoria.skinhairdresser.Room.Measurement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static java.security.AccessController.getContext;

public class ManageActivity extends AppCompatActivity {
    //고객 진단 관리 화면
    private LineChart chart, chart2, chart3, chart4, chart5, chart6, chart7, chart8;
    float average = 0;
    Button finish_btn;
    AppDatabase db;
    Calendar calendar;
    ArrayList<Entry> oil,moisture,pg,hole,tone,sensitivity,fold,color_washing;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        calendar = Calendar.getInstance();

        oil = new ArrayList<>();
        moisture = new ArrayList<>();
        pg = new ArrayList<>();
        hole = new ArrayList<>();
        tone = new ArrayList<>();
        sensitivity = new ArrayList<>();
        fold = new ArrayList<>();
        color_washing = new ArrayList<>();


        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "DB").allowMainThreadQueries().build();
//        if (db.measurementDao().findByYmd(
//                calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH) + 1,
//                calendar.get(Calendar.DAY_OF_MONTH)) != null) {
//            Log.e("있다 ~!","ㅎㅎ");
////            db.measurementDao().update(measurement);
//        } else {
//            // 없으면 DB INSERT
//            Log.e("없으니까 ~!","ㅎㅎ");
////            db.measurementDao().insertAll(measurement);
//        }


        List<Measurement> all = db.measurementDao().getAll();
        for (Measurement m : all) {

            oil.add(new Entry(count, m.oil));
            moisture.add(new Entry(count, m.oil));
            pg.add(new Entry(count, m.oil));
            hole.add(new Entry(count, m.oil));
            tone.add(new Entry(count, m.oil));
            sensitivity.add(new Entry(count, m.oil));
            fold.add(new Entry(count, m.oil));
            color_washing.add(new Entry(count, m.oil));
            count++;

            Log.e("DB", "cid : " + m.cid + " , " + "year : " + m.year + " , " + "month : " + m.month + " , " + "day : " + m.day + " , "
                    + "pg : " + m.pg + " , " + "moisture : " + m.moisture + " , " + "hole : " + m.hole + " , " + "tone : " + m.tone + " , "
                    + "sensitivity : " + m.sensitivity + " , " + "fold : " + m.fold + " , " + "oil : " + m.oil);
        }

        finish_btn = findViewById(R.id.finish_btn);

        chart = findViewById(R.id.lineChart);
        chart2 = findViewById(R.id.lineChart2);
        chart3 = findViewById(R.id.lineChart3);
        chart4 = findViewById(R.id.lineChart4);
        chart5 = findViewById(R.id.lineChart5);
        chart6 = findViewById(R.id.lineChart6);
        chart7 = findViewById(R.id.lineChart7);
        chart8 = findViewById(R.id.lineChart8);

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int val = (int) (Math.random() * 3)+1;
            values.add(new Entry(i, val));
        }

        settingChart(chart,values);
        settingChart(chart2,values);
        settingChart(chart3,values);
        settingChart(chart4,values);
        settingChart(chart5,values);
        settingChart(chart6,values);
        settingChart(chart7,values);
        settingChart(chart8,values);



        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    void settingChart(LineChart chart, ArrayList<Entry> values){
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(false);
        chart.setPinchZoom(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.animateXY(2000,2000);

        Legend l = chart.getLegend();
        l.setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);
        xAxis.setAxisMaximum(30);

        YAxis yLAxis =chart.getAxisLeft();
        yLAxis.setDrawLabels(false);
        yLAxis.setDrawAxisLine(false);
        yLAxis.setDrawGridLines(false);
        yLAxis.setAxisMaximum(5);
        yLAxis.setAxisMinimum(0);

        YAxis yRAxis = chart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);



        average = (float) 2.5;

        LineDataSet set1;
        set1 = new LineDataSet(values, null);

        LimitLine limitLine = new LimitLine(average);
        limitLine.setLineColor(ContextCompat.getColor(this, R.color.limit_color));

        yLAxis.addLimitLine(limitLine);
        yLAxis.setDrawLimitLinesBehindData(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);


        LineData data = new LineData(dataSets);

        set1.setDrawFilled(false);
        set1.setDrawValues(false);
        set1.setDrawCircleHole(false);
        set1.setDrawCircles(false);
        set1.setColor(ContextCompat.getColor(this, R.color.main_brown));
        set1.setFillColor(R.color.main_brown);


        chart.setData(data);
    }
}