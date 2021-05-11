package com.corporation8793.skinhairdresser;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.corporation8793.skinhairdresser.Thread.ConnectedThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class LetOutActivity extends AppCompatActivity {
    //토출하기 화면
    Button finish_btn,bluetooth_btn,let_out_btn,confirm_btn;

    BluetoothAdapter btAdapter;
    private final static int REQUEST_ENABLE_BT = 1;
    private String POOP_VALUE = "";

    Set<BluetoothDevice> pairedDevices;
    ArrayAdapter<String> btArrayAdapter;
    ArrayList<String> deviceAddressArray;

    ConnectedThread connectedThread = null;
    AlertDialog.Builder builder;
    ListView pair_lv;

    TextView main_text, detail_text;
    boolean check = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_let_out);

        sharedPreferences = getSharedPreferences("appData",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        finish_btn = findViewById(R.id.finish_btn);
        bluetooth_btn = findViewById(R.id.bluetooth_btn);
        let_out_btn = findViewById(R.id.let_out_btn);
        confirm_btn = findViewById(R.id.confirm_btn);
        main_text = findViewById(R.id.main_text);
        detail_text = findViewById(R.id.detail_text);

        getPermission();

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bluetooth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"bluetooth 연결",Toast.LENGTH_SHORT).show();
                onBT();

                // Pairing
                pairing();


                checking();
            }
        });

        let_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ssss","!");
                if (check){
                    main_text.setText("화장품 토출중 ...");
                    detail_text.setText("토출이 완료될 때까지 손을 빼지 마세요");
                }


                if(connectedThread != null) {
                    int A = (int) (Math.random() *3) +1;
                    int B = (int) (Math.random() *3) +1;
                    int C = (int) (Math.random() *3) +1;
                    int D = (int) (Math.random() *3) +1;
                    int E = (int) (Math.random() *3) +1;
                    int F = (int) (Math.random() *3) +1;

                    editor.putInt("A",sharedPreferences.getInt("A",20)-A);
                    editor.putInt("B",sharedPreferences.getInt("B",20)-B);
                    editor.putInt("C",sharedPreferences.getInt("C",20)-C);
                    editor.putInt("D",sharedPreferences.getInt("D",20)-D);
                    editor.putInt("E",sharedPreferences.getInt("E",20)-E);
                    editor.putInt("F",sharedPreferences.getInt("F",20)-F);

                    POOP_VALUE += "A" + A + " / ";
                    POOP_VALUE += "B" + B + " / ";
                    POOP_VALUE += "C" + C + " / ";
                    POOP_VALUE += "D" + D + " / ";
                    POOP_VALUE += "E" + E + " / ";
                    POOP_VALUE += "F" + F;

                    connectedThread.write(POOP_VALUE);
                    editor.commit();
                    Log.e("poop",POOP_VALUE);
                    POOP_VALUE = "";
                }
//                Toast.makeText(getApplicationContext(),"토출하기",Toast.LENGTH_SHORT).show();
            }
        });

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LetOutActivity.this, ConfirmActivity.class);
                startActivity(intent);
                finish();
            }
        });




        // Enable bluetooth


    }



    void checking() {
        // Check PMA, SOK
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Log.e("btAdapter",btAdapter.getState()+"");
                if (true) {
                    if (connectedThread != null) {
                        if (connectedThread.getPMA()) {
                            new Handler().postDelayed(() -> {
                                //딜레이 후 시작할 코드 작성

                                Log.e("토출","준비");
                                    main_text.setText("토출이 준비되었습니다");
                                    detail_text.setText("토출하기 버튼을 다시 눌러주세요");
//                                    Toast.makeText(LetOutActivity.this, "토출이 준비되었습니다", Toast.LENGTH_SHORT).show();
//                                    tv_status.setText("토출이 준비되었습니다" + "\n토출값을 입력하고 \'토출하기\' 버튼을 눌러주세요.");
//                                    btn_poop.setEnabled(true);

                            }, 6000);
                            connectedThread.setPMA(false);
                        } else if (connectedThread.getSOK()) {
                            new Handler().postDelayed(() -> {
                                //딜레이 후 시작할 코드 작성
                                Log.e("토출","완료");
                                main_text.setText("토출이 완료되었습니다");
                                detail_text.setText("");
//                                Toast.makeText(LetOutActivity.this, "토출이 완료되었습니다", Toast.LENGTH_SHORT).show();
//                                tv_status.setText("토출이 완료되었습니다" + "\n토출값 : " + POOP_VALUE);
                                POOP_VALUE = "";
                            }, 1000);
                            connectedThread.setSOK(false);
                        }
                    }
                    handler.postDelayed(this,1000);
                }
            }
        },1000);
    }

    void pairing() {
        builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.pairing, null);
        builder.setView(dialogView);

        pair_lv = dialogView.findViewById(R.id.pair_lv);

        // show paired devices
        btArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        deviceAddressArray = new ArrayList<>();

        btArrayAdapter.clear();
        if(deviceAddressArray!=null && !deviceAddressArray.isEmpty()){ deviceAddressArray.clear(); }
        pairedDevices = btAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                btArrayAdapter.add(deviceName);
                deviceAddressArray.add(deviceHardwareAddress);
            }
        }

        pair_lv.setAdapter(btArrayAdapter);

        AlertDialog dialog = builder.create();
        dialog.show();

        pair_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"연결중입니다...",Toast.LENGTH_SHORT).show();
                dialog.cancel();
                //Toast.makeText(getApplicationContext(), btArrayAdapter.getItem(position) + " try...", Toast.LENGTH_SHORT).show();

                final String name = btArrayAdapter.getItem(position); // get name
                final String address = deviceAddressArray.get(position); // get address
                boolean flag = true;

                BluetoothDevice device = btAdapter.getRemoteDevice(address);
                BluetoothSocket btSocket = null;

                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

                // create & connect socket
                try {
                    btSocket = device.createRfcommSocketToServiceRecord(uuid);
                    btSocket.connect();
                } catch (IOException e) {
                    flag = false;
                    Toast.makeText(getApplicationContext(), "connection failed!", Toast.LENGTH_SHORT).show();
//                    dialog.cancel();
//                    tv_status.setText("토출기와 연결하는데 실패했습니다");
                    e.printStackTrace();
                }

                if(flag){
                    connectedThread = new ConnectedThread(btSocket);
                    connectedThread.start();

                    new Handler().postDelayed(() -> {
                        //딜레이 후 시작할 코드 작성
                        Toast.makeText(getApplicationContext(), "connected to " + name, Toast.LENGTH_SHORT).show();
                        let_out_btn.setEnabled(true);
                        bluetooth_btn.setBackgroundResource(R.drawable.bluetooth_btn);

                        main_text.setText("토출기와 연결하는데 성공했습니다" + "\n토출기 : " + name);
//                        editor.putBoolean("bluetooth",true);
//                        editor.commit();

//                        dialog.cancel();
                    }, 2000);
                }
            }
        });
    }

    void onBT() {
        // Enable bluetooth
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    void getPermission() {
        // Get permission
        String[] permission_list = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        ActivityCompat.requestPermissions(LetOutActivity.this, permission_list,  1);
    }
}