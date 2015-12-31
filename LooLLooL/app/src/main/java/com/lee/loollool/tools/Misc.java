package com.lee.loollool.tools;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

import com.lee.loollool.bean.MeDevice;

/**
 * Created by derek on 18/12/15.
 */
public class Misc {
    private static final String TAG = Misc.class.getSimpleName();
    private static DisplayMetrics mDisplayMetrics;

    public static DisplayMetrics getScreenDensity(Activity activity) {
        if (mDisplayMetrics != null) return mDisplayMetrics;
        
        mDisplayMetrics = activity.getApplicationContext().getResources().getDisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getMetrics(mDisplayMetrics);
        float screenWidth = mDisplayMetrics.widthPixels;
        float screenHeight = mDisplayMetrics.heightPixels;
        MeDevice.sharedManager().setWidth((int) screenWidth);
        MeDevice.sharedManager().setHeight((int) screenHeight);
        return mDisplayMetrics;
    }
    //public static DisplayMetrics getScreenDensity() throws NullPointerException {
    //    if (mDisplayMetrics == null) {
    //        throw new NullPointerException("mDisplayMetrics not set, please run getScreenDensity(Activity activity) first");
    //    }
    //    return mDisplayMetrics;
    //}
}
