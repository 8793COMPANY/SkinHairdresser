package com.victoria.skinhairdresser;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.victoria.skinhairdresser.BroadcastReceiver.WifiReceiver;
import com.victoria.skinhairdresser.cw.CustomWebView;


import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;

import at.grabner.circleprogress.CircleProgressView;

import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.INTERNET;

public class StartActivity extends AppCompatActivity {
    // global
    // TODO : 주소 수정 필요 !!
    final String HOST = "http://192.168.4.1";
    Map<String, String> extraHeaders = new HashMap<String, String>();
    private final int PERMISSIONS_REQUEST_RESULT = 1;

    WifiReceiver wifiReceiver = new WifiReceiver();
    String networkSSID = "ESP32-Access-Point";
    WifiManager wifiManager;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, INTERNET)) {
                //권한을 거절하면 재 요청을 하는 함수
            } else {
                ActivityCompat.requestPermissions(this, new String[]{INTERNET, ACCESS_WIFI_STATE}, PERMISSIONS_REQUEST_RESULT);
            }
        }

        // Wifi check receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, filter);
        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);

        // local
        wv = findViewById(R.id.wv);
        wv.setForeground(getResources().getDrawable(R.drawable.start_circle));
        progress_circular = findViewById(R.id.progress_circular);
        progress_circular.setMaxValue(second);
        btn_stream = findViewById(R.id.start_stream_btn);
        start_20_sec = findViewById(R.id.start_20_sec);
        start_20_sec.setEnabled(false);
        start_close_btn = findViewById(R.id.start_close_btn);
        start_check_wifi = findViewById(R.id.start_check_wifi);
        start_tv_1 = findViewById(R.id.start_tv_1);
        start_tv_2 = findViewById(R.id.start_tv_2);

        final Handler handler_main = new Handler();
        final Handler handler = new Handler();
        final Handler handler2 = new Handler();
        final Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        // Wifi check receiver
        handler_main.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (true) {
                    handler_main.postDelayed(this,1000);

                    runOnUiThread(() -> {
                        if (wifiManager.getConnectionInfo().getSSID().equals("\"" + networkSSID + "\"")) {
                            start_check_wifi.setBackground(getResources().getDrawable(R.drawable.start_check_wifi_on));
                        } else {
                            start_check_wifi.setBackground(getResources().getDrawable(R.drawable.start_check_wifi_off));
                        }
                    });
                }
            }
        },1000);

        start_check_wifi.setOnClickListener(v -> {
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        });

        start_close_btn.setOnClickListener(v -> {
            finish();
        });

        btn_stream.setOnClickListener(v -> {
            if(v.isSelected()) {
                wv.stopLoading();
                wv.loadUrl("about:blank");
                wv.setForeground(getResources().getDrawable(R.drawable.start_circle));
                start_tv_1.setText("스트리밍 대기 중 ···");
                start_tv_2.setText("스트리밍 시작하기 버튼을 눌러주세요");
                v.setSelected(false);
                start_20_sec.setEnabled(false);
            } else {
                if (!wifiManager.getConnectionInfo().getSSID().equals("\"" + networkSSID + "\"")) {
                    Toast.makeText(this, "측정기 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                    vibrator.vibrate(100);

                    wv.stopLoading();
                    wv.loadUrl("about:blank");
                    wv.setForeground(getResources().getDrawable(R.drawable.start_circle));
                    start_tv_1.setText("스트리밍 대기 중 ···");
                    start_tv_2.setText("스트리밍 시작하기 버튼을 눌러주세요");
                } else {
                    //
                    PlayHttpStream(HOST + "/hmirror");
                    //wv.stopLoading();
                    //wv.loadUrl("about:blank");

                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (true) {
                                PlayHttpStream(HOST + ":81/stream");
                            }
                        }
                    },1000);
                    //
                    wv.setForeground(getResources().getDrawable(android.R.color.transparent));
                    start_tv_1.setText("측정 준비 완료");
                    start_tv_2.setText("측정하기 버튼을 눌러주세요");
                }
                v.setSelected(true);
                start_20_sec.setEnabled(true);
            }
        });

        start_20_sec.setOnClickListener(v -> {
            if (!wifiManager.getConnectionInfo().getSSID().equals("\"" + networkSSID + "\"")) {
                Toast.makeText(this, "측정기 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                vibrator.vibrate(100);

                wv.stopLoading();
                wv.loadUrl("about:blank");
                wv.setForeground(getResources().getDrawable(R.drawable.start_circle));
                start_tv_1.setText("스트리밍 대기 중 ···");
                start_tv_2.setText("스트리밍 시작하기 버튼을 눌러주세요");
            } else {
                start_20_sec.setEnabled(false);
                btn_stream.setEnabled(false);
                start_tv_1.setText("첫 번째 측정 중 ···");
                start_tv_2.setText("측정이 완료될 때까지 피부에서 떼지 마세요");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (prg < second && wifiReceiver.getWifiConnection()) {
                            handler.postDelayed(this,100);

                            prg += 100;

                            Log.e("progress_circular", "progress: " + prg);
                            progress_circular.setValue(prg);
                        } else {
                            if (!wifiManager.getConnectionInfo().getSSID().equals("\"" + networkSSID + "\"")) {
                                Toast.makeText(StartActivity.this, "측정기 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                                vibrator.vibrate(100);
                                progress_circular.setValue(0);

                                wv.stopLoading();
                                wv.loadUrl("about:blank");
                                wv.setForeground(getResources().getDrawable(R.drawable.start_circle));
                                start_tv_1.setText("스트리밍 대기 중 ···");
                                start_tv_2.setText("스트리밍 시작하기 버튼을 눌러주세요");
                                btn_stream.setSelected(true);
                                start_20_sec.setEnabled(true);
                                btn_stream.setEnabled(true);
                            } else {
                                wv.stopLoading();
                                PlayHttpStream(HOST + "/hmirror");
                                vibrator.vibrate(100);
                                Toast.makeText(StartActivity.this, "측정이 완료되었습니다", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                                startActivity(intent);
                                //recreate();
                                finish();
                            }
                        }
                    }
                },100);
            }
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