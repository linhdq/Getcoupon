package com.vn.getcoupon.getcouponvn.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.activity.HomeActivity;
import com.vn.getcoupon.getcouponvn.intef.OnNetworkChangeReceiver;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service implements OnNetworkChangeReceiver {
    //
    private Context context;

    //
    private Timer timer;

    // Notification
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    private static final long TIME_COUNTDOWN = 1000 * 60 * 60 * 24;

    // Broadcast Receiveer
    private NetworkBroadcastReceiver networkBroadcastReceiver;
    private IntentFilter intentFilter;

    //
    private boolean isWaiting;

    public NotificationService(){
        Log.d("fuck", "create");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        countdownForNotification();
        return super.onStartCommand(intent, flags, startId);
    }


    private void init(){
        //
        context = this;
        isWaiting = false;

        //
        timer = new Timer();

        //
        networkBroadcastReceiver = new NetworkBroadcastReceiver(this);
        intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        //
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, HomeActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        // Notification build
        builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_discount)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo))
                .setContentTitle("New Coupon")
                .setContentText("Co Coupon moi, vao app de check")
                .setVibrate(new long[] {1000,1000})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLights(Color.BLUE, 1000, 3000)
                .setContentIntent(pendingIntent);

    }

    @Override
    public void onNetworkChangeReceiver() {
        if (isWaiting){
            unregisterReceiver(networkBroadcastReceiver);
            notificationManager.notify(1, builder.build());
            isWaiting = false;
            timer.cancel();
            countdownForNotification();
        }
    }

    private void countdownForNotification(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (networkBroadcastReceiver.checkNetworkStatus(context)){
                    notificationManager.notify(1, builder.build());
                } else {
                    timer.cancel();
                    isWaiting = true;
                    registerReceiver(networkBroadcastReceiver, intentFilter);
                }
            }
        },TIME_COUNTDOWN, TIME_COUNTDOWN);
    }
}
