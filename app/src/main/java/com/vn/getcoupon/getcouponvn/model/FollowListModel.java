package com.vn.getcoupon.getcouponvn.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by linhdq on 5/14/17.
 */

public class FollowListModel extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
    private String logoUrl;
    private int logoId;
    private int type;

    public static FollowListModel create(String id, String name, String logoUrl, int logoId, int type) {
        FollowListModel model = new FollowListModel();
        model.id = id;
        model.name = name;
        model.logoUrl = logoUrl;
        model.logoId = logoId;
        model.type = type;
        return model;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public int getType() {
        return type;
    }

    public int getLogoId() {
        return logoId;
    }
}
