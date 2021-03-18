package com.victoria.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class ManageActivity extends AppCompatActivity {
    //고객 진단 관리 화면
    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);


        chart = findViewById(R.id.lineChart);
        chart.getDescription().setEnabled(false);
        chart.invalidate();
        chart.setPinchZoom(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);

        YAxis yLAxis =chart.getAxisLeft();
        yLAxis.setDrawLabels(false);
        yLAxis.setDrawAxisLine(false);
        yLAxis.setDrawGridLines(false);
        YAxis yRAxis = chart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);


        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            float val = (float) (Math.random() * 10);
            values.add(new Entry(i, val));
        }

        LineDataSet set1;
        set1 = new LineDataSet(values, "");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets

        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        // black lines and points

        chart.setBackgroundColor(Color.WHITE);

        set1.setDrawFilled(false);
        set1.setDrawValues(false);
        set1.setDrawCircleHole(false);
        set1.setDrawCircles(false);
        set1.setColor(R.color.main_brown);
        set1.setFillColor(R.color.main_brown);



        // set data
        chart.setData(data);





    }
}