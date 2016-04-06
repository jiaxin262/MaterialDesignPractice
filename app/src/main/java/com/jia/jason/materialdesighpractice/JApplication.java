package com.jia.jason.materialdesighpractice;

import android.app.Application;
import android.content.Context;

/**
 * Created by xin.jia
 * since 2016/3/29
 */
public class JApplication extends Application {

    private static Context mContext;

    public JApplication() {
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext = this;
    }

    public static Context getContext() {
        if(mContext == null) {
            throw new RuntimeException("you must extends JApplication !!! ");
        } else {
            return mContext;
        }
    }

}
