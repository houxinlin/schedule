package com.hxl.course.entity;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Comparator;

public class PlanEntity implements Comparable<PlanEntity> {
    private Calendar mStartTimer;
    private Calendar mEndTimer;

    private String mDescribe;

    public String getDescribe() {
        return mDescribe;
    }

    public void setDescribe(String describe) {
        mDescribe = describe;
    }

    public PlanEntity(Calendar startTimer, Calendar endTimer, String describe) {
        mStartTimer = startTimer;
        mEndTimer = endTimer;
        mDescribe = describe;
    }

    public Calendar getStartTimer() {
        return mStartTimer;
    }

    public void setStartTimer(Calendar startTimer) {
        mStartTimer = startTimer;
    }

    public Calendar getEndTimer() {
        return mEndTimer;
    }

    public void setEndTimer(Calendar endTimer) {
        mEndTimer = endTimer;
    }

    public String conver(){
        return mStartTimer.get(Calendar.HOUR_OF_DAY)+":"+mStartTimer.get(Calendar.MINUTE);
    }
    @Override
    public String toString() {
        return "{\"mStartTimer\""+":\""+conver()+"\","+"\"mDescribe\":"+"\""+mDescribe+"\"}";
    }

    @Override
    public int compareTo(PlanEntity o) {

        if (o.getStartTimer().get(Calendar.HOUR_OF_DAY)>this.getStartTimer().get(Calendar.HOUR_OF_DAY)){
            return -1;
        }else if (o.getStartTimer().get(Calendar.HOUR_OF_DAY) ==this.getStartTimer().get(Calendar.HOUR_OF_DAY)){
            return -(o.getStartTimer().get(Calendar.MINUTE)-this.getStartTimer().get(Calendar.MINUTE));
        }else {
            return 1;
        }

    }
}
