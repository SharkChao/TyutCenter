package com.tyutcenter.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Created by Admin on 2018/3/29.
 */

public class AppUtils {

    private static Boolean isDebug = true;

    public static boolean isDebug() {
        return isDebug == null ? false : isDebug.booleanValue();
    }

    /**
     * Sync lib debug with app's debug value. Should be called in module Application
     *
     * @param context
     */
    public static void syncIsDebug(Context context) {
        if (isDebug == null) {
            isDebug = context.getApplicationInfo() != null &&
                    (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
    }
}