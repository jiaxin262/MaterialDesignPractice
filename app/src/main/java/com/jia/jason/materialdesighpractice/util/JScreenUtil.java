package com.jia.jason.materialdesighpractice.util;

import android.util.DisplayMetrics;

import com.jia.jason.materialdesighpractice.JApplication;

/**
 * Created by xin.jia
 * since 2016/4/6
 */
public class JScreenUtil {

    private static volatile int screenWidth = 0;
    private static volatile int screenHeight = 0;

    public static int getScreenWidth() {
        if(screenWidth > 0){
            return screenWidth;
        }else{
            DisplayMetrics dm = JApplication.getContext().getResources().getDisplayMetrics();
            screenWidth = dm.widthPixels;
            screenHeight = dm.heightPixels;
            return screenWidth;
        }
    }

    public static int getScreenHeight(){
        if(screenHeight > 0){
            return screenHeight;
        }else{
            DisplayMetrics dm = JApplication.getContext().getResources().getDisplayMetrics();
            screenWidth = dm.widthPixels;
            screenHeight = dm.heightPixels;
            return screenHeight;
        }
    }
}
