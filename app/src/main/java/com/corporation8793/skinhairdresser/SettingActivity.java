package com.corporation8793.skinhairdresser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.corporation8793.skinhairdresser.BroadcastReceiver.WifiReceiver;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CHANGE_NETWORK_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;

public class SettingActivity extends AppCompatActivity {

    //내 정보 화면
    // 내 정보 화면
    // 글로벌
    private final int PERMISSIONS_REQUEST_RESULT = 1;

    AppCompatButton back_btn;
    LinearLayout setting_edit_info;
    LinearLayout setting_go_start, setting_go_manage;
    Switch setting_letOut, setting_start, setting_login;

    // WIFI settings
    WifiReceiver wifiReceiver = new WifiReceiver();
    String networkSSID = "ESP32-Access-Point";
    String networkPass = "123456789";
    int netId = 0;
    Boolean check_flag = false;
    // WIFI over O versions
    NetworkRequest networkRequest = null;
    ConnectivityManager.NetworkCallback networkCallback;
    // WIFI under O versions
    WifiManager wifiManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // 로컬
        back_btn = findViewById(R.id.back_btn);
        setting_edit_info = findViewById(R.id.setting_edit_info);
        setting_go_start = findViewById(R.id.setting_go_start);
        setting_go_manage = findViewById(R.id.setting_go_manage);
        setting_letOut = findViewById(R.id.setting_letOut);
        setting_start = findViewById(R.id.setting_start);
        setting_login = findViewById(R.id.setting_login);

        // permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION)) {
                //권한을 거절하면 재 요청을 하는 함수
            } else {
                ActivityCompat.requestPermissions(this, new String[]{CHANGE_WIFI_STATE, CHANGE_NETWORK_STATE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_RESULT);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            networkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(@NonNull Network network) {
                    Log.d("넷 콜백", "onAvailable");
                }

                @Override
                public void onUnavailable() {
                    Toast.makeText(SettingActivity.this, "연결이 취소되었습니다", Toast.LENGTH_SHORT).show();
                    setting_start.setChecked(false);
                }
            };
        }

        // Wifi check receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, filter);
        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);

        Handler handler_main = new Handler();

        wifiManager.setWifiEnabled(true);

        // 스위치 제어
        setting_letOut.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // ON
                Toast.makeText(this, "토출기 ON", Toast.LENGTH_SHORT).show();
            } else {
                // OFF
                Toast.makeText(this, "토출기 OFF", Toast.LENGTH_SHORT).show();
            }
        });

        setting_start.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // ON
                check_flag = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Intent panelIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivityForResult(panelIntent, 1);
                } else {
                    WifiConfiguration wifiConfig = new WifiConfiguration();
                    wifiConfig.SSID = String.format("\"%s\"", networkSSID);
                    wifiConfig.preSharedKey = String.format("\"%s\"", networkPass);
                    wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);

                    netId = wifiManager.addNetwork(wifiConfig);
                    wifiManager.disconnect();

                    if (wifiManager.enableNetwork(netId, true)) {
                        handler_main.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (check_flag) {
                                    handler_main.postDelayed(this,2000);

                                    if (wifiReceiver.getWifiConnection()) {
                                        Toast.makeText(SettingActivity.this, "측정기가 연결되었습니다", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SettingActivity.this, "측정기 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                                    }
                                    check_flag = !check_flag;
                                }
                            }
                        },2000);
                    }

                    wifiManager.reconnect();
                }
            } else {
                // OFF
                check_flag = false;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                } else {
                    Toast.makeText(this, "측정기 연결이 해제되었습니다", Toast.LENGTH_SHORT).show();

                    wifiManager.disconnect();
                    wifiManager.disableNetwork(netId);
                    wifiManager.removeNetwork(netId);
                    wifiManager.reconnect();
                }
            }
        });

        setting_login.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // ON
                Toast.makeText(this, "자동 로그인 ON", Toast.LENGTH_SHORT).show();
            } else {
                // OFF
                Toast.makeText(this, "자동 로그인 OFF", Toast.LENGTH_SHORT).show();
            }
        });


        // 인텐트 이동
        back_btn.setOnClickListener(v -> {
            finish();
        });

        setting_edit_info.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
            intent.putExtra("detail","edit");
            startActivity(intent);
            finish();
        });

        setting_go_start.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
            startActivity(intent);
            finish();
        });

        setting_go_manage.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ManageActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!wifiManager.isWifiEnabled()) {
            setting_start.setChecked(false);
        } else {
            if(!wifiManager.getConnectionInfo().getSSID().equals("\"" + networkSSID + "\"")) {
                Toast.makeText(SettingActivity.this, "측정기와 연결해주세요", Toast.LENGTH_SHORT).show();
                setting_start.setChecked(false);
            } else {
                Toast.makeText(SettingActivity.this, "측정기가 연결되었습니다", Toast.LENGTH_SHORT).show();
            }
        }
    }
}