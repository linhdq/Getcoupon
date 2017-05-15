package com.vn.getcoupon.getcouponvn.model.json_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by linhdq on 5/15/17.
 */

public class JSONCouponItem {
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
    private String couponTitle;

    @SerializedName("content")
    @Expose
    private String couponContent;

    @SerializedName("logo")
    @Expose
    private String couponLogoUrl;

    @SerializedName("coupon_type")
    @Expose
    private String couponType;

    @SerializedName("code")
    @Expose
    private String couponCode;

    @SerializedName("destination_url")
    @Expose
    private String couponUrl;

    @SerializedName("persen_success")
    @Expose
    private String persenSuccess;

    @SerializedName("used")
    @Expose
    private String couponUsed;

    @SerializedName("expires")
    @Expose
    private String couponExpires;

    public static JSONCouponItem create (String couponId, String storeId, String storeName, String couponTitle, String couponContent, String couponLogoUrl, String couponType, String couponCode, String couponUrl, String persenSuccess, String couponUsed, String couponExpires) {
        JSONCouponItem jsonCouponItem = new JSONCouponItem();
        jsonCouponItem.couponId = couponId;
        jsonCouponItem.storeId = storeId;
        jsonCouponItem.storeName = storeName;
        jsonCouponItem.couponTitle = couponTitle;
        jsonCouponItem.couponContent = couponContent;
        jsonCouponItem.couponLogoUrl = couponLogoUrl;
        jsonCouponItem.couponType = couponType;
        jsonCouponItem.couponCode = couponCode;
        jsonCouponItem.couponUrl = couponUrl;
        jsonCouponItem.persenSuccess = persenSuccess;
        jsonCouponItem.couponUsed = couponUsed;
        jsonCouponItem.couponExpires = couponExpires;
        return jsonCouponItem;
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

    public String getCouponTitle() {
        return couponTitle;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle;
    }

    public String getCouponContent() {
        return couponContent;
    }

    public void setCouponContent(String couponContent) {
        this.couponContent = couponContent;
    }

    public String getCouponLogoUrl() {
        return couponLogoUrl;
    }

    public void setCouponLogoUrl(String couponLogoUrl) {
        this.couponLogoUrl = couponLogoUrl;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponUrl() {
        return couponUrl;
    }

    public void setCouponUrl(String couponUrl) {
        this.couponUrl = couponUrl;
    }

    public String getPersenSuccess() {
        return persenSuccess;
    }

    public void setPersenSuccess(String persenSuccess) {
        this.persenSuccess = persenSuccess;
    }

    public String getCouponUsed() {
        return couponUsed;
    }

    public void setCouponUsed(String couponUsed) {
        this.couponUsed = couponUsed;
    }

    public String getCouponExpires() {
        return couponExpires;
    }

    public void setCouponExpires(String couponExpires) {
        this.couponExpires = couponExpires;
    }
}
