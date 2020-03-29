package com.hxl.course.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hxl.course.R;

public class ClassScheduleCard extends RelativeLayout {
    private String[] mWeekName = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private TextView[] mWeekTestView = new TextView[7];
    private CenterTextView mNodeName;
    private ClassScheduleCardContent  mContentView;

    public ClassScheduleCard(Context context) {
        super(context);
        initViews();
    }

    public ClassScheduleCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public ClassScheduleCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {

        /**
         * 设置左上角信息
         */
        mNodeName = new CenterTextView(getContext());
        mNodeName.setText("节数");
        mNodeName.setTextAppearance(getContext(),R.style.TopWeekStyleStyle);
        LinearLayout mWeekLayout = new LinearLayout(getContext());
        int nodeWidth = (int) getResources().getDimension(R.dimen.classScheduleNodeWidth);
        int height = (int) getResources().getDimension(R.dimen.classScheduleCardTopWeekHeight);
        LinearLayout.LayoutParams nodeParamsName = new LinearLayout.LayoutParams(nodeWidth, height);
        mNodeName.setLayoutParams(nodeParamsName);
        mWeekLayout.addView(mNodeName);

        /**
         * 水平排放周视图
         */
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        mWeekLayout.setOrientation(LinearLayout.HORIZONTAL);
        mWeekLayout.setLayoutParams(params);
        mWeekLayout.setBackgroundColor(getResources().getColor(R.color.classScheduleCardTopAndLefColor));

        for (int i = 0; i < mWeekTestView.length; i++) {
            mWeekTestView[i] = new CenterTextView(getContext());
            mWeekLayout.addView(mWeekTestView[i]);
            TextView textView = mWeekTestView[i];
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            textView.setLayoutParams(layoutParams);
            textView.setText(mWeekName[i]);
            textView.setTextAppearance(getContext(),R.style.TopWeekStyleStyle);
        }
        /**
         * 自身添加周视图
         */
        this.addView(mWeekLayout);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView=   new ClassScheduleCardContent(getContext());
        addView(mContentView,params1);

    }

}
