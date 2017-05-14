package com.vn.getcoupon.getcouponvn.model;

/**
 * Created by linhdq on 4/22/17.
 */

public class ItemFollowDrawerModel {
    private int icon;
    private String title;

    public ItemFollowDrawerModel(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
