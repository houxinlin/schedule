package com.hxl.course.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;

public class CenterTextView  extends AppCompatTextView {
    public CenterTextView(Context context) {
        super(context);
        init();
    }

    public CenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void  init(){
        setGravity(Gravity.CENTER);
    }
}
