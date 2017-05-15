package com.vn.getcoupon.getcouponvn.model.json_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ZYuTernity on 14/05/2017.
 */

public class JSONCategoryItem {
    @SerializedName("category_id")
    @Expose
    private String categoryId;

    @SerializedName("name")
    @Expose
    private String categoryName;

    public static JSONCategoryItem create (String categoryId, String categoryName) {
        JSONCategoryItem jsonCategoryItem = new JSONCategoryItem();
        jsonCategoryItem.categoryId = categoryId;
        jsonCategoryItem.categoryName = categoryName;
        return jsonCategoryItem;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
