package com.vn.getcoupon.getcouponvn.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.activity.SplashActivity;
import com.vn.getcoupon.getcouponvn.intef.OnNetworkChangeReceiver;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ZYuTernity on 29/05/2017.
 */

public class NotificationHandle extends BroadcastReceiver implements OnNetworkChangeReceiver {
    //
    private Context context;

    //
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCES_KEY = "Notification checker";
    private static final String IS_WAITING = "isShowed";

    // Notification
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;

    // Broadcast Receiver
    private NetworkBroadcastReceiver networkBroadcastReceiver;
    private IntentFilter intentFilter;


    @Override
    public void onReceive(Context context, Intent intent) {
        init(context);
        Log.d("fuck", "check network status: " + String.valueOf(networkBroadcastReceiver.checkNetworkStatus(context)));
        if (getBooleanIsWaitingForNetwork() || !networkBroadcastReceiver.checkNetworkStatus(context)) {
            waitingForNetwork();
        } else {
            showNotification();
        }
    }

    private boolean getBooleanIsWaitingForNetwork() {
        return sharedPreferences.getBoolean(IS_WAITING, false);
    }

    private void init(Context context) {
        //
        this.context = context;

        //
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, MODE_PRIVATE);

        //
        networkBroadcastReceiver = new NetworkBroadcastReceiver(this);
        intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        //
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, SplashActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        // Notification build
        builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_discount)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo))
                .setContentTitle("Getcoupon.vn")
                .setContentText("Vừa thêm coupon, voucher mới ...")
                .setVibrate(new long[]{1000, 1000})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLights(Color.BLUE, 1000, 3000)
                .setContentIntent(pendingIntent);
    }

    @Override
    public void onNetworkChangeReceiver() {
        Log.d("fuck", "show from waiting network");
        context.getApplicationContext().unregisterReceiver(networkBroadcastReceiver);
        showNotification();
    }

    private void waitingForNetwork() {
        Log.d("fuck", "is waiting for network");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_WAITING, true);
        editor.apply();
        context.getApplicationContext().registerReceiver(networkBroadcastReceiver, intentFilter);
    }

    private void showNotification() {
        Log.d("fuck", "showing noti");
        notificationManager.notify(1, builder.build());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_WAITING, false);
        editor.apply();
    }
}
