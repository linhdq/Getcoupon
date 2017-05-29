package com.vn.getcoupon.getcouponvn.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.vn.getcoupon.getcouponvn.intef.OnNetworkChangeReceiver;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by ZYuTernity on 15/05/2017.
 */

public class NetworkBroadcastReceiver extends BroadcastReceiver {

    private OnNetworkChangeReceiver onNetworkChangeReceiver;
    private NetworkInfo networkInfo;

    //
        private static final long TIME_OF_A_DAY = 1000 * 60 * 60 * 24;
//    private static final long TIME_OF_A_DAY = 1000 * 60;

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
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            notificationConfig(context);
        }
    }

    public boolean checkNetworkStatus(Context context) {
        networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED);
    }

    private void notificationConfig(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationHandle.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), TIME_OF_A_DAY, pendingIntent);
    }
}
