package com.vn.getcoupon.getcouponvn.utilities;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by linhdq on 2/21/17.
 */

public class StringUtils {
    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d");
    }
}
