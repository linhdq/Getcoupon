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

    public static FollowListModel create(String id, String name, String logoUrl) {
        FollowListModel model = new FollowListModel();
        model.id = id;
        model.name = name;
        model.logoUrl = logoUrl;
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
}
