package com.vn.getcoupon.getcouponvn.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.vn.getcoupon.getcouponvn.intef.OnNetworkChangeReceiver;

/**
 * Created by ZYuTernity on 15/05/2017.
 */

public class NetworkBroadcastReceiver extends BroadcastReceiver {

    private OnNetworkChangeReceiver onNetworkChangeReceiver;
    private NetworkInfo networkInfo;

    public NetworkBroadcastReceiver(OnNetworkChangeReceiver onNetworkChangeReceiver) {
        this.onNetworkChangeReceiver = onNetworkChangeReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                onNetworkChangeReceiver.onNetworkChangeReceiver();
                Log.d("fuck", "onReceive");
            }
        }
    }

    public boolean checkNetworkStatus(Context context) {
        networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED);
    }
}
