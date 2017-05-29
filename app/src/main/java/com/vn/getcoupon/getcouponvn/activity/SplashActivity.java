package com.vn.getcoupon.getcouponvn.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.database.DBContext;
import com.vn.getcoupon.getcouponvn.model.json_model.JSONCouponItem;
import com.vn.getcoupon.getcouponvn.network.GetService;
import com.vn.getcoupon.getcouponvn.network.ServiceFactory;
import com.vn.getcoupon.getcouponvn.services.NotificationHandle;
import com.vn.getcoupon.getcouponvn.utilities.Constant;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    //
    private DBContext dbContext;
    private GetService getService;

    // Notification
    private AlarmManager alarmManager;
    private Calendar calendar;
    private Intent intent;
    private PendingIntent pendingIntent;
    private static final long TIME_OF_A_DAY = 1000 * 60 * 60 * 24;
//    private static final long TIME_OF_A_DAY = 1000 * 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        //
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //
        getListCoupon();
    }

    private void init() {
        //network
        getService = ServiceFactory.getInstance().createService(GetService.class);
        //
        dbContext = DBContext.getInst();
        // Notification Service
        notificationConfig();
    }

    private void getListCoupon() {
        Call<List<JSONCouponItem>> call = getService.callJsonListCoupon();
        call.enqueue(new Callback<List<JSONCouponItem>>() {
            @Override
            public void onResponse(Call<List<JSONCouponItem>> call, Response<List<JSONCouponItem>> response) {
                if (response.code() == Constant.OK_STATUS) {
                    List<JSONCouponItem> jsonCouponItems = response.body();
                    if (jsonCouponItems != null && jsonCouponItems.size() != 0) {
                        dbContext.deleteAllCoupons();
                        dbContext.addCoupon(jsonCouponItems);
                        //
                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<JSONCouponItem>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void notificationConfig() {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(this, NotificationHandle.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), TIME_OF_A_DAY, pendingIntent);
    }
}
