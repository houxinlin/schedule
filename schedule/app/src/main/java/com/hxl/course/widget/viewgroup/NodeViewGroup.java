package com.hxl.course.widget.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hxl.course.common.IOTimerModif;
import com.hxl.course.widget.BaseLinearLayout;

public class NodeViewGroup extends BaseLinearLayout {

    public NodeViewGroup(Context context) {
        super(context);
    }

    public NodeViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NodeViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init() {
        setOrientation(LinearLayout.VERTICAL);
    }


}
