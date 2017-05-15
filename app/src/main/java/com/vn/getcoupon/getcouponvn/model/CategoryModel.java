package com.vn.getcoupon.getcouponvn.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by linhdq on 5/15/17.
 */

public class CategoryModel extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
    private int logo;

    public static CategoryModel create(String id, String name, int logo) {
        CategoryModel model = new CategoryModel();
        model.id = id;
        model.name = name;
        model.logo = logo;
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

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
