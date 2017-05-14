package com.vn.getcoupon.getcouponvn.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by linhdq on 5/14/17.
 */

public class ServiceFactory {
    private Retrofit retrofit;

    private static ServiceFactory instance = new ServiceFactory();

    public static ServiceFactory getInstance(){
        return instance;
    }

    private ServiceFactory() {

        retrofit = new Retrofit.Builder()
                .baseUrl(API.ROOT_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <ServiceClass> ServiceClass createService(Class<ServiceClass> serviceClass){
        return retrofit.create(serviceClass);
    }
}
