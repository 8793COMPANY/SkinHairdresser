package com.victoria.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.victoria.skinhairdresser.cw.CustomWebView;


import at.grabner.circleprogress.CircleProgressView;

import static android.Manifest.permission.INTERNET;

public class StartActivity extends AppCompatActivity {
    // global
    // TODO : 주소 수정 필요 !!
    final String HOST = "http://192.168.4.1";
    private final int PERMISSIONS_REQUEST_RESULT = 1;

    // cw
    CustomWebView wv;

    // cpv
    CircleProgressView progress_circular;
    float second = 20000;
    float prg = 0;

    // widget
    TextView start_tv_1, start_tv_2;
    ImageButton start_close_btn, start_check_wifi;
    LinearLayout btn_stream, start_20_sec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, INTERNET)) {
                //권한을 거절하면 재 요청을 하는 함수
            } else {
                ActivityCompat.requestPermissions(this, new String[]{INTERNET}, PERMISSIONS_REQUEST_RESULT);
            }
        }

        // local
        wv = findViewById(R.id.wv);
        progress_circular = findViewById(R.id.progress_circular);
        progress_circular.setMaxValue(second);
        btn_stream = findViewById(R.id.start_stream_btn);
        start_20_sec = findViewById(R.id.start_20_sec);
        start_close_btn = findViewById(R.id.start_close_btn);
        start_check_wifi = findViewById(R.id.start_check_wifi);
        start_tv_1 = findViewById(R.id.start_tv_1);
        start_tv_2 = findViewById(R.id.start_tv_2);

        final Handler handler = new Handler();

        start_close_btn.setOnClickListener(v -> {
            finish();
        });

        btn_stream.setOnClickListener(v -> {
            if(v.isSelected()) {
                wv.stopLoading();
                wv.loadUrl("about:blank");
            } else {
                PlayHttpStream(HOST + ":81/stream");
                v.setSelected(true);
            }
        });

        start_20_sec.setOnClickListener(v -> {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (prg < second) {
                        handler.postDelayed(this,1000);

                        prg += 1000;
                        Log.e("progress_circular", "progress: " + prg);
                        progress_circular.setValue(prg);
                    } else {
                        Toast.makeText(StartActivity.this, "측정이 완료되었습니다", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            },1000);
        });

        // wv no touch setting
        wv.setOnTouchListener((v, event) -> true);
    }

    //play http stream
    private void PlayHttpStream(String httpUrl){
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(httpUrl);
    }
}