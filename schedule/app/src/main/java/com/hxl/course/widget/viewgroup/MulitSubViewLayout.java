package com.hxl.course.widget.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MulitSubViewLayout extends RelativeLayout {
    private static final String TAG = "MulitSubViewLayout";
    public MulitSubViewLayout(Context context) {
        super(context);
    }

    public MulitSubViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MulitSubViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent: ");
        return super.onInterceptTouchEvent(ev);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: ");
        return super.onTouchEvent(event);
    }
}
