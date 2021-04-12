package com.example.foodinventorydemo.utils;

import android.content.Context;
import android.util.TypedValue;

public class Utils {
    public static int dpToPx(float dp, Context ctx) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, ctx.getResources().getDisplayMetrics());
    }
}
