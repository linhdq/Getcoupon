package com.vn.getcoupon.getcouponvn.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.activity.SplashActivity;
import com.vn.getcoupon.getcouponvn.intef.OnNetworkChangeReceiver;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service implements OnNetworkChangeReceiver {
    //
    private Context context;

    //
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCES_KEY = "Notification checker";
    private static final String IS_SHOWED = "isShowed";

    //
    private Timer timer;
    private Calendar calendar;

    // Notification
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    private static final long TIME_OF_A_DAY = 1000 * 60 * 60 * 24;

    // Broadcast Receiveer
    private NetworkBroadcastReceiver networkBroadcastReceiver;
    private IntentFilter intentFilter;


    public NotificationService() {
        Log.d("fuck", "create");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("fuck", "destroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("fuck", "boolean " + String.valueOf(sharedPreferences.getBoolean(IS_SHOWED, false)));
        if (!sharedPreferences.getBoolean(IS_SHOWED, false)) {
            countdownForNotification(checkTimeRemaning(calendar));
        } else {
            waitingForNetwork();
        }
        return START_STICKY_COMPATIBILITY;
    }

    private void init() {
        //
        context = this;
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_KEY, MODE_PRIVATE);

        //
        timer = new Timer();

        calendar = Calendar.getInstance();

        //
        networkBroadcastReceiver = new NetworkBroadcastReceiver(this);
        intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        //
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, SplashActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        // Notification build
        builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_discount)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo))
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
        unregisterReceiver(networkBroadcastReceiver);
        showNotification();
        countdownForNotification(checkTimeRemaning(calendar));
    }

    private void countdownForNotification(long delay) {
        Log.d("fuck", "start countdown in " + delay + " mili second");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (networkBroadcastReceiver.checkNetworkStatus(context)) {
                    Log.d("fuck", "show time run out");
                    showNotification();
                    countdownForNotification(TIME_OF_A_DAY);
                } else {
                    waitingForNetwork();
                }
            }
        }, delay);
    }

    private long checkTimeRemaning(Calendar calendar) {
        calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();

        Log.d("fuck", "now is " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "  " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + 1 + "/" + calendar.get(Calendar.YEAR));

        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        long notificationTime = calendar.getTimeInMillis();

        Log.d("fuck", "notificationTime is " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "  " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));

        long timeRemaining = notificationTime - now;
        if (timeRemaining >= 0) {
            Log.d("fuck", "case 1");
            return timeRemaining;
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            notificationTime = calendar.getTimeInMillis();
            Log.d("fuck", "case 2");
            Log.d("fuck", "notificationTime is " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "  " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
            return notificationTime - now;
        }
    }

    private void waitingForNetwork() {
        Log.d("fuck", "is waiting for network");
        timer.cancel();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_SHOWED, true);
        editor.apply();
        registerReceiver(networkBroadcastReceiver, intentFilter);
    }

    private void showNotification() {
        Log.d("fuck", "showing noti");
        notificationManager.notify(1, builder.build());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_SHOWED, false);
        editor.apply();
        timer.cancel();
    }
}
