package com.corporation8793.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class RecommendWebView extends AppCompatActivity {
    AppCompatButton finish_btn, refresh_btn;
    WebView wv;
    WifiManager wifiManager;
    String networkSSID = "ESP32-Access-Point";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_web_view);

        finish_btn = findViewById(R.id.finish_btn);
        refresh_btn = findViewById(R.id.refresh_btn);

        wv = findViewById(R.id.wv);
        wv.getSettings().setJavaScriptEnabled(true);

        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);

        wv_control();

        refresh_btn.setOnClickListener(v -> {
            wv_control();
        });

        finish_btn.setOnClickListener(v -> {
            finish();
        });
    }

    void wv_control() {
        final Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        if (wifiManager.getConnectionInfo().getSSID().equals("\"" + networkSSID + "\"")) {
            wifiManager.disconnect();
            Toast.makeText(this, "인터넷 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
            vibrator.vibrate(100);
            wv.stopLoading();
            wv.loadUrl("about:blank");
            wv.setVisibility(View.GONE);
            refresh_btn.setVisibility(View.VISIBLE);
        } else {
            wv.setVisibility(View.VISIBLE);
            refresh_btn.setVisibility(View.GONE);
            vibrator.vibrate(100);
            wv.loadUrl("http://www.ozhealthcare.co.kr/default/mall/mall1.php?topmenu=p&mode=list&cate_code=CA100002");
        }
    }
}