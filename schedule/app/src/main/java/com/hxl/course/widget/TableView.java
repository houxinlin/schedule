package com.hxl.course.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hxl.course.common.IDayClassListEvents;
import com.hxl.course.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class TableView extends LinearLayout implements IDayClassListEvents {
    private List<InDayClassList> mInDayClassLists ;
    public TableView(Context context) {
        super(context);
        init();
    }

    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }
    private  void init(){
        mInDayClassLists =new ArrayList<>();
        setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < 7; i++) {
            addView(createLinearLayout(i+1));
        }

    }
    private ViewGroup createLinearLayout(int index){
        InDayClassList inDayClassList = new InDayClassList(getContext(),index);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        inDayClassList.setLayoutParams(params);
        inDayClassList.setIDayClassListEvents(this);
        mInDayClassLists.add(inDayClassList);
        return inDayClassList;
    }

    @Override
    public void clearSelect() {
        for (int i = 0; i < mInDayClassLists.size(); i++) {
                mInDayClassLists.get(i).clearAddStateView();
        }
    }

}
