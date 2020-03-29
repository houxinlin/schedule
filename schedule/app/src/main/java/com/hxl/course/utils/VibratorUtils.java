package com.hxl.course.utils;

import android.content.Context;
import android.os.Vibrator;

import static android.content.Context.VIBRATOR_SERVICE;

public class VibratorUtils {
    public static void vibrator(Context context,long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }
    public static void vibrator(Context context) {
        vibrator(context,15);
    }
}
