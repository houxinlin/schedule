package com.hxl.course.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.hxl.course.MainActivity;
import com.hxl.course.R;
import com.hxl.course.entity.PlanEntity;
import com.hxl.course.services.TimerTaskService;
import com.hxl.course.storage.PlanStorage;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;

public class PlanActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    private  NumberPicker mHourPicker;
    private  NumberPicker mMinutePicker;
    public static  final int ADD_OK=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_plan);
        getWindow().setStatusBarColor(Color.BLACK);
        initViews();


    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }

    private  void initViews(){
        mHourPicker = findViewById(R.id.np_hour);
        mMinutePicker = findViewById(R.id.np_minute);

        //设置最大值
        mHourPicker.setMaxValue(24);
        mMinutePicker.setMaxValue(59);
        //mNumberPicker
        mHourPicker.setMinValue(1);
        mMinutePicker.setMinValue(0);
        //设置当前值
        mHourPicker.setValue(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        //设置滑动监听





        setNumberPickerDividerColor(mMinutePicker)   ;
        setNumberPickerDividerColor(mHourPicker)   ;
    }
    private void setNumberPickerDividerColor(NumberPicker numberPicker) {
        NumberPicker picker = numberPicker;
        Field mSelectionDivider = null;
        Field[] declaredFields = NumberPicker.class.getDeclaredFields();

        try {

            mSelectionDivider = NumberPicker.class.getDeclaredField("mSelectionDivider");
            mSelectionDivider.setAccessible(true);
            mSelectionDivider.set(numberPicker,new ColorDrawable(this.getResources().getColor(android.R.color.transparent)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public void onSaveClick(View view) {
        String text = ((EditText) findViewById(R.id.ed_describe)).getText().toString();
        if (text.length()==0){
            Toast.makeText(this, "请输入描述", Toast.LENGTH_SHORT).show();
            return;
        }
        PlanStorage planStorage =new PlanStorage();
        PlanEntity planEntity = new PlanEntity(gengCalender(mHourPicker.getValue(), mMinutePicker.getValue()), null, text);
        Toast.makeText(this, ""+planStorage.setValue(planEntity,null), Toast.LENGTH_SHORT).show();
        setResult(ADD_OK);
        finish();
    }
    private  Calendar gengCalender(int hour,int minute){
        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        return calendar;
    }
}
