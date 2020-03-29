package com.hxl.course.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hxl.course.common.ITask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimerTaskService extends Service {
    String TAG="TimerTaskService";
    /**
     * 所有触发的任务时间点。
     */
    private Map<Calendar, List<ITask>> mTimerTaskList;

    public TimerTaskService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return  null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mTimerTaskList =new HashMap<>();
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initClassTimerReady();
        initAlarmClock();

        Log.i(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 初始化上课前一分钟，下课前一分钟，用来在软件上签到。
     */
    private  void initClassTimerReady(){


    }

    /**
     * 初始化闹钟
     */
    private  void initAlarmClock(){


    }
}
