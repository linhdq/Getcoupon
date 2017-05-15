package com.vn.getcoupon.getcouponvn.model.json_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by linhdq on 5/15/17.
 */

public class JSONCouponItem extends RealmObject {
    @SerializedName("coupon_id")
    @Expose
    private String couponId;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("logo")
    @Expose
    private String logoUrl;
    @SerializedName("coupon_type")
    @Expose
    private String couponType;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("destination_url")
    @Expose
    private String destinationUrl;
    @SerializedName("persen_success")
    @Expose
    private String persenSuccess;
    @SerializedName("used")
    @Expose
    private String used;
    @SerializedName("expires")
    @Expose
    private String expiresDate;

    public static JSONCouponItem create(String couponId, String storeId, String storeName, String title, String content, String logoUrl, String couponType, String code, String destinationUrl, String persenSuccess, String used, String expiresDate) {
        JSONCouponItem model = new JSONCouponItem();
        model.couponId = couponId;
        model.storeId = storeId;
        model.storeName = storeName;
        model.title = title;
        model.content = content;
        model.logoUrl = logoUrl;
        model.couponType = couponType;
        model.code = code;
        model.destinationUrl = destinationUrl;
        model.persenSuccess = persenSuccess;
        model.used = used;
        model.expiresDate = expiresDate;
        return model;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDestinationUrl() {
        return destinationUrl;
    }

    public void setDestinationUrl(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }

    public String getPersenSuccess() {
        return persenSuccess;
    }

    public void setPersenSuccess(String persenSuccess) {
        this.persenSuccess = persenSuccess;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(String expiresDate) {
        this.expiresDate = expiresDate;
    }
}
