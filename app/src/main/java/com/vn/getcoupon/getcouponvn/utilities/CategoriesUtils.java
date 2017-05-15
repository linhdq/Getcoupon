package com.vn.getcoupon.getcouponvn.utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.vn.getcoupon.getcouponvn.R;

/**
 * Created by ZYuTernity on 14/05/2017.
 */

public class CategoriesUtils {
    public static Drawable getIconById(Context context, String categoryId){
        switch (Integer.parseInt(categoryId)){
            case 51:
                return ContextCompat.getDrawable(context, R.drawable.ic_hot_coupon);
            case 38:
                return ContextCompat.getDrawable(context, R.drawable.ic_education);
            case 36:
                return ContextCompat.getDrawable(context, R.drawable.ic_beauty);
            case 35:
                return ContextCompat.getDrawable(context, R.drawable.ic_notebook);
            case 33:
                return ContextCompat.getDrawable(context, R.drawable.ic_travel);
            case 32:
                return ContextCompat.getDrawable(context, R.drawable.ic_tools);
            case 29:
                return ContextCompat.getDrawable(context, R.drawable.ic_hi_tech);
            case 44:
                return ContextCompat.getDrawable(context, R.drawable.ic_money_bag);
            case 45:
                return ContextCompat.getDrawable(context, R.drawable.ic_meal);
            case 46:
                return ContextCompat.getDrawable(context, R.drawable.ic_sport);
            case 31:
                return ContextCompat.getDrawable(context, R.drawable.ic_refrigerator);
            case 37:
                return ContextCompat.getDrawable(context, R.drawable.ic_fashion);
            case 34:
                return ContextCompat.getDrawable(context, R.drawable.ic_mom_and_baby);
            case 106:
                return ContextCompat.getDrawable(context, R.drawable.ic_pencil);
            case 220:
                return ContextCompat.getDrawable(context, R.drawable.ic_transport);
            default:
                return null;
        }
    }
}
