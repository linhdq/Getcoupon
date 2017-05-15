package com.vn.getcoupon.getcouponvn.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.model.json_model.JSONCouponItem;
import com.vn.getcoupon.getcouponvn.network.API;
import com.vn.getcoupon.getcouponvn.network.GetService;
import com.vn.getcoupon.getcouponvn.network.ServiceFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by linhdq on 4/22/17.
 */

public class SaleCodeFragment extends Fragment {

    //
    private GetService getService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale_code, container, false);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getCoupons();
    }

    private void init(View view) {
        //
        getService = ServiceFactory.getInstance().createService(GetService.class);
    }

    private void getCoupons() {
        Call<List<JSONCouponItem>> call = getService.callJsonCouponList();
        call.enqueue(new Callback<List<JSONCouponItem>>() {
            @Override
            public void onResponse(Call<List<JSONCouponItem>> call, Response<List<JSONCouponItem>> response) {
                for (JSONCouponItem jsonCouponItem : response.body()){
                    Log.d("fucked", jsonCouponItem.getCouponTitle());
                }
            }

            @Override
            public void onFailure(Call<List<JSONCouponItem>> call, Throwable t) {

            }
        });
    }
}
