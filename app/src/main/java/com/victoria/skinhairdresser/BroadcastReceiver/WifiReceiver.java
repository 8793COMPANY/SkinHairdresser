package com.victoria.skinhairdresser.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiReceiver extends BroadcastReceiver {
    private Context mContext;

    public Boolean WifiConnection = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;

        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // Wifi is connected
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ssid = wifiInfo.getSSID();

                Log.e("인터넷", " -- Wifi connected --- " + " SSID " + ssid );
                if (ssid.equals("\"ESP32-Access-Point\"")) {
                    WifiConnection = true;
                    Log.e("인터넷", "측정기 ON" + WifiConnection);
                } else {
                    WifiConnection = false;
                    Log.e("인터넷", "측정기 OFF" + WifiConnection);
                }
            }
        }
        else if (intent.getAction().equalsIgnoreCase(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);

            if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
                Log.e("인터넷", " ----- Wifi  Disconnected ----- ");
                WifiConnection = false;
                Log.e("인터넷", "측정기 OFF" + WifiConnection);
            }
        }
    }

    public Boolean getWifiConnection() {
        return WifiConnection;
    }
}