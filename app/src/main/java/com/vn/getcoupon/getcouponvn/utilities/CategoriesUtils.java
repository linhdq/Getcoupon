package com.vn.getcoupon.getcouponvn.utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.vn.getcoupon.getcouponvn.R;

/**
 * Created by ZYuTernity on 14/05/2017.
 */

public class CategoriesUtils {
    public static int getResourceById(Context context, String categoryId){
        switch (Integer.parseInt(categoryId)){
            case 51:
                return R.mipmap.ic_discount;
            case 38:
                return R.mipmap.ic_mortarboard;
            case 36:
                return R.mipmap.ic_hair_dryer;
            case 35:
                return R.mipmap.ic_notebook;
            case 33:
                return R.mipmap.ic_beach;
            case 32:
                return R.mipmap.ic_tools;
            case 29:
                return R.mipmap.ic_desktop;
            case 44:
                return R.mipmap.ic_money_bag;
            case 45:
                return R.mipmap.ic_restaurant;
            case 46:
                return R.mipmap.ic_football;
            case 31:
                return R.mipmap.ic_refrigerator;
            case 37:
                return R.mipmap.ic_shirt;
            case 34:
                return R.mipmap.ic_newborn;
            case 106:
                return R.mipmap.ic_pencil;
            case 220:
                return R.mipmap.ic_delivery_truck;
            default:
                return 0;
        }
    }
}
