package com.vn.getcoupon.getcouponvn.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by linhdq on 5/14/17.
 */

public class FollowCategoryListModel extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private int logo;

    public static FollowCategoryListModel create(int id, String name, int logo) {
        FollowCategoryListModel model = new FollowCategoryListModel();
        model.id = id;
        model.name = name;
        model.logo = logo;
        return model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
