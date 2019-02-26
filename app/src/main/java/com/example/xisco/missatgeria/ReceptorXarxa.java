package com.example.xisco.missatgeria;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ReceptorXarxa extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean connectat3G = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean connectatWifi = networkInfo.isConnected();
        if (networkInfo != null && connectat3G) {
            Toast.makeText(context, "Xarxa ok, conectat3G", Toast.LENGTH_LONG).show();
        } else if (networkInfo != null && connectatWifi){
            Toast.makeText(context, "Xarxa ok, conectat Wifi", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Xarxa no disponible", Toast.LENGTH_LONG).show();
        }
    }
}
