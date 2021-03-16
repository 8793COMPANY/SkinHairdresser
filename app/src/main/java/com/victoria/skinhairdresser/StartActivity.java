package com.victoria.skinhairdresser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.victoria.skinhairdresser.cw.CustomWebView;


import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;

import at.grabner.circleprogress.CircleProgressView;

import static android.Manifest.permission.INTERNET;

public class StartActivity extends AppCompatActivity {
    // global
    // TODO : 주소 수정 필요 !!
    final String HOST = "http://192.168.4.1";
    Map<String, String> extraHeaders = new HashMap<String, String>();
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
        wv.setScaleX(1.5f);
        wv.setScaleY(1.5f);
        progress_circular = findViewById(R.id.progress_circular);
        progress_circular.setMaxValue(second);
        btn_stream = findViewById(R.id.start_stream_btn);
        start_20_sec = findViewById(R.id.start_20_sec);
        start_close_btn = findViewById(R.id.start_close_btn);
        start_check_wifi = findViewById(R.id.start_check_wifi);
        start_tv_1 = findViewById(R.id.start_tv_1);
        start_tv_2 = findViewById(R.id.start_tv_2);

        final Handler handler = new Handler();

        final Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        start_close_btn.setOnClickListener(v -> {
            finish();
        });

        btn_stream.setOnClickListener(v -> {
            if(v.isSelected()) {
                wv.stopLoading();
                wv.loadUrl("about:blank");
                start_tv_1.setText("스트리밍 대기 중 ···");
                start_tv_2.setText("스트리밍 시작하기 버튼을 눌러주세요");
                v.setSelected(false);
            } else {
                PlayHttpStream(HOST + ":81/stream");
                start_tv_1.setText("측정 준비 완료");
                start_tv_2.setText("측정하기 버튼을 눌러주세요");
                v.setSelected(true);
            }
        });

        start_20_sec.setOnClickListener(v -> {
            start_20_sec.setEnabled(false);
            btn_stream.setEnabled(false);
            start_tv_1.setText("첫 번째 측정 중 ···");
            start_tv_2.setText("측정이 완료될 때까지 피부에서 떼지 마세요");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (prg < second) {
                        handler.postDelayed(this,1000);

                        prg += 1000;
                        Log.e("progress_circular", "progress: " + prg);
                        progress_circular.setValue(prg);
                    } else {
                        vibrator.vibrate(100);
                        Toast.makeText(StartActivity.this, "측정이 완료되었습니다", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                        startActivity(intent);
                        recreate();
                    }
                }
            },1000);
        });

        // wv no touch setting
        wv.setOnTouchListener((v, event) -> true);
    }

    //play http stream
    private void PlayHttpStream(String httpUrl){
        extraHeaders.put("X-Requested-With", "doodong.me");

        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(httpUrl, extraHeaders);
    }
}