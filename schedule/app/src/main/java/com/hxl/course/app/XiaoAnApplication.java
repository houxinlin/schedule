package com.hxl.course.app;

import android.app.Application;

import com.hxl.course.utils.SpUtils;

public class XiaoAnApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SpUtils.getInstance().setContext(this);
    }
}
