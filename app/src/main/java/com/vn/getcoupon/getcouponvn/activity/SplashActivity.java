package com.vn.getcoupon.getcouponvn.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.database.DBContext;
import com.vn.getcoupon.getcouponvn.model.json_model.JSONCouponItem;
import com.vn.getcoupon.getcouponvn.network.GetService;
import com.vn.getcoupon.getcouponvn.network.ServiceFactory;
import com.vn.getcoupon.getcouponvn.utilities.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    //
    private DBContext dbContext;
    private GetService getService;

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
        //
        getListCoupon();
    }

    private void init() {
        //network
        getService = ServiceFactory.getInstance().createService(GetService.class);
        //
        dbContext = DBContext.getInst();
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
}
