package com.vn.getcoupon.getcouponvn.network;

import com.vn.getcoupon.getcouponvn.model.json_model.JSONStoreList;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by linhdq on 5/14/17.
 */

public interface GetService {
    @POST(API.LIST_STORE)
    @Headers({API.HEADERS})
    Call<JSONStoreList> callJsonLogin();

}
