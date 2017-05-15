package com.vn.getcoupon.getcouponvn.network;

import com.vn.getcoupon.getcouponvn.model.json_model.JSONStoreItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by linhdq on 5/14/17.
 */

public interface GetService {
    @GET(API.LIST_STORE)
    @Headers({API.HEADERS})
    Call<List<JSONStoreItem>> callJsonListStore();

}
