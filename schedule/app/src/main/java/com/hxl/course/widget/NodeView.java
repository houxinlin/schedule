package com.hxl.course.widget;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hxl.course.R;
import com.hxl.course.common.IOTimerModif;
import com.hxl.course.utils.SpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class NodeView extends CenterTextView {
    private IOTimerModif mIOTimerModif;
    public static final String TAG = "NodeView";
    private String mStartTimer = "";
    private String mEndTimer = "";
    private int mNode;
    private  long mFirstUpTimer;

    public NodeView(Context context) {
        super(context);
    }

    public NodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init() {
        super.init();

    }

    /**
     * 显示不同字体大小的信息
     */
    private void calcul() {


        String info = mNode + "\n\r" + mStartTimer + "\n\r" + mEndTimer;

        SpannableStringBuilder spannable = new SpannableStringBuilder(info);
        spannable.setSpan(new AbsoluteSizeSpan(40), 0, mNode >= 10 ? 2 : 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new AbsoluteSizeSpan(28), mNode >= 10 ? 2 : 1, info.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        setText(spannable);
    }

    /**
     * 可能设置每节课的时长
     *
     * 如果存在时长则设置这节课的开始时间，结束时间自动增加到没节课的时长
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            mFirstUpTimer=System.currentTimeMillis();

        }
        int oneClassTimerLong = SpUtils.getInstance().getInteger("one_class_timer");
        if (event.getAction() == MotionEvent.ACTION_UP) {
           if (System.currentTimeMillis()-mFirstUpTimer>500){
                mIOTimerModif.onTimerDelete(mNode);
           }else{
               //如果为-1，则表示没有设置时间
               if (oneClassTimerLong == -1) {
                   showSetClassTimerDialog();
               }else {
                   //显示时间选择器
                   Calendar instance = Calendar.getInstance();
                   TimePickerDialog pickerDialog =new TimePickerDialog(getContext(),R.style.TimerDialogStyle, new TimePickerDialog.OnTimeSetListener() {
                       @Override
                       public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                           //保存时间
                           saveNodeTimer(hourOfDay,minute);
                       }
                   },instance.get(Calendar.HOUR_OF_DAY),instance.get(Calendar.MINUTE),true);
                   pickerDialog.show();
                   Toast.makeText(getContext(), ""+oneClassTimerLong, Toast.LENGTH_SHORT).show();
               }
           }
        }
        return true;
    }

    /**
     * 保存时间到本地。并刷新
     * @param hour
     * @param minute
     */
    private void saveNodeTimer(int hour,int minute){
        try {
            JSONObject object =new JSONObject();

            object.put("start",((hour>=10)?hour:"0"+hour)+":"+(minute>=10?minute:"0"+minute));
            JSONArray array =new JSONArray();
            String node_timers = SpUtils.getInstance().getString("node_timers");
            if (node_timers.length()!=0){
                 array =new JSONArray(node_timers);
            }
            array.put(packingInfo(object.toString()));
            SpUtils.getInstance().setString("node_timers",array.toString());
            onTimerModify();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private String packingInfo(String info){
        try {
            JSONObject object =new JSONObject();
            object.put(mNode+"",info);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void showSetClassTimerDialog() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_set_timer,null,false);

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("设置课节时长")
                .setView(view)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SlideView viewById = view.findViewById(R.id.slideview);
                        if (viewById.getCurrentValue()>0){
                            SpUtils.getInstance().setInteger("one_class_timer",viewById.getCurrentValue());
                        }
                    }
                })
                .show();
    }

    public IOTimerModif getIOTimerModif() {
        return mIOTimerModif;
    }

    public void setIOTimerModif(IOTimerModif IOTimerModif) {
        mIOTimerModif = IOTimerModif;
    }

    public void onTimerModify(){
        if (mIOTimerModif!=null){
            mIOTimerModif.onTimerModif();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setStartTimer(String startTimer) {
        mStartTimer = startTimer;
        calcul();
    }

    public void setEndTimer(String endTimer) {
        mEndTimer = endTimer;
        calcul();
    }

    public void setNode(int node) {
        mNode = node;
        calcul();
    }
    public  void clearTimer(){
        mStartTimer="";
        mEndTimer="";
        calcul();
    }
}
