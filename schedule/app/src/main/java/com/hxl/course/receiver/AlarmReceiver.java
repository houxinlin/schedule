package com.hxl.course.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hxl.course.MainActivity;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {
    private static final  String TAG="TAG";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: ");
        Intent intent2 = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent2, 0);


        Calendar calendar =Calendar.getInstance();

        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        // 设置闹钟

        am.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+5000,pendingIntent);

    }
}
