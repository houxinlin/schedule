package com.hxl.course;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hxl.course.activity.PlanActivity;
import com.hxl.course.adapter.PlanAdapter;
import com.hxl.course.dialog.ClassEditDialog;
import com.hxl.course.entity.PlanEntity;
import com.hxl.course.receiver.AlarmReceiver;
import com.hxl.course.storage.PlanStorage;
import com.hxl.course.utils.SpUtils;
import com.hxl.course.widget.ClassScheduleCardContent;
import com.hxl.course.widget.SlideView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnLongClickListener, View.OnClickListener {
    private int value = 0;
    private RecyclerView mPlanRecycleView;
    private List<PlanEntity> mPlanEntities;
    private PlanAdapter planAdapter;
    private PlanStorage mPlanStorage;
    private static final int ADD_PLAN_REQUEST_CODE = 1000;
    private  View mClassLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_main);


        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);




        mPlanRecycleView = findViewById(R.id.plan_recycle);
        mClassLong =findViewById(R.id.setting_class_long);
        mClassLong.setOnClickListener(this);
        mPlanStorage = new PlanStorage();
        mPlanEntities = new ArrayList<>();
        planAdapter = new PlanAdapter(mPlanEntities, this);
        mPlanRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mPlanRecycleView.setAdapter(planAdapter);
        mPlanRecycleView.setTag("PlanRecycleView");

        initPlanList();
        load();


    }
    private  void load(){
        Integer one_class_timer = SpUtils.getInstance().getInteger("one_class_timer");
        ((TextView) mClassLong.findViewWithTag("value")).setText(one_class_timer+"");


    }

    public void onAddPlanActivityClick(View view) {
        Intent intent = new Intent(this, PlanActivity.class);
        startActivityForResult(intent, ADD_PLAN_REQUEST_CODE);
    }

    private void sort() {
        Collections.sort(mPlanEntities);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PLAN_REQUEST_CODE && resultCode == PlanActivity.ADD_OK) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initPlanList();
                }
            }, 0, 800);
        }
    }

    private void initPlanList() {
        mPlanEntities.clear();
        mPlanEntities.addAll(mPlanStorage.getList());
        sort();
        planAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        return false;
    }
    private void showSetClassTimerDialog() {
        final View view = LayoutInflater.from(this).inflate(R.layout.dialog_set_timer,null,false);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("设置课节时长")
                .setView(view)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SlideView viewById = view.findViewById(R.id.slideview);
                        if (viewById.getCurrentValue()>0){
                            SpUtils.getInstance().setInteger("one_class_timer",viewById.getCurrentValue());
                            load();
                            ((ClassScheduleCardContent) findViewById(R.id.mulit).findViewWithTag("ClassScheduleCardContent")).loadNodeTimer();
                        }
                    }
                })
                .show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_class_long:
                showSetClassTimerDialog();
                break;
        }
    }
}
