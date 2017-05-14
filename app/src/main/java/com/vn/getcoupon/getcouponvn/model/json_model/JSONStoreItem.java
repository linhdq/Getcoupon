package com.vn.getcoupon.getcouponvn.model.json_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by linhdq on 5/14/17.
 */

public class JSONStoreItem extends RealmObject {
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("logo")
    @Expose
    private String logoUrl;

    public static JSONStoreItem create(String id, String name, String logoUrl) {
        JSONStoreItem model = new JSONStoreItem();
        model.id = id;
        model.name = name;
        model.logoUrl = logoUrl;
        return model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
