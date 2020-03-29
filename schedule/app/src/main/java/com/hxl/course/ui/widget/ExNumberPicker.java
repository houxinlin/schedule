package com.hxl.course.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

public class ExNumberPicker extends NumberPicker {
    public ExNumberPicker(Context context) {
        super(context);
    }

    public ExNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    /**
     * 修改字的大小和颜色
     */
    private void updateView(View view){
        if( view instanceof EditText){
            EditText editText = (EditText) view;
            editText.setTextColor(Color.parseColor("#ffffff")); //修改字的颜色
            editText.setTextSize(25);//修改字的大小
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
