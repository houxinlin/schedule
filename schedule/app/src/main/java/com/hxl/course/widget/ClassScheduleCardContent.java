package com.hxl.course.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.hxl.course.R;
import com.hxl.course.common.IOTimerModif;
import com.hxl.course.utils.SpUtils;
import com.hxl.course.widget.viewgroup.NodeViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClassScheduleCardContent extends ScrollView implements IOTimerModif {
    private NodeViewGroup mNodeViewGroup;

    private List<NodeView> mNodeViews = new ArrayList<>();

    public ClassScheduleCardContent(Context context) {
        super(context);
        init();
    }

    public ClassScheduleCardContent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClassScheduleCardContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setTag("ClassScheduleCardContent");
        setVerticalScrollBarEnabled(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
        //添加主要内容
        inflate(getContext(), R.layout.class_schedule_card_content, this);

        mNodeViewGroup = findViewById(R.id.ll_node);

        int nodeWidth = (int) getResources().getDimension(R.dimen.classScheduleNodeWidth);
        int nodeHeight = (int) getResources().getDimension(R.dimen.classScheduleNodeHeight);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(nodeWidth, nodeHeight);
        //十二节课序号
        for (int i = 0; i < 12; i++) {
            NodeView textView = new NodeView(getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(params);
            textView.setNode(i + 1);
            textView.setTextAppearance(getContext(), R.style.TopWeekStyleStyle);
            mNodeViewGroup.addView(textView);
            mNodeViews.add(textView);
            /**
             * 节点时间事件将回传到这里
             */
            textView.setIOTimerModif(this);
        }
        /**
         * 加载时间
         */
        loadNodeTimer();
    }

    /**
     * 删除时间
     *
     * @param index
     */
    public void deleteNodeForIndex(int index) {
        try {
            Log.i("TAG", "deleteNodeForIndex: ");
            String node_timers = SpUtils.getInstance().getString("node_timers");
            JSONArray nodeTimers = new JSONArray();
            if (node_timers.length() != 0) {
                nodeTimers = new JSONArray(node_timers);

            }

            for (int j = 0; j < nodeTimers.length(); j++) {
                try {
                    JSONObject object = new JSONObject(nodeTimers.getString(j));
                    if (object.has(index + "")) {
                        nodeTimers.remove(j);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            SpUtils.getInstance().setString("node_timers", nodeTimers.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 加载时间
     */
    public void loadNodeTimer() {
        Log.i("TAG2", "loadNodeTimer: ");
        String node_timers = SpUtils.getInstance().getString("node_timers");
        JSONArray nodeTimers = new JSONArray();
        if (node_timers.length() != 0) {
            try {
                nodeTimers = new JSONArray(node_timers);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        SimpleDateFormat simpleDateFormat  =new SimpleDateFormat("HH:mm");
        int oneClassTimerLong = SpUtils.getInstance().getInteger("one_class_timer");

        for (int i = 0; i < mNodeViews.size(); i++) {
            mNodeViews.get(i).clearTimer();
            for (int j = 0; j < nodeTimers.length(); j++) {
                try {
                    JSONObject object = new JSONObject(nodeTimers.getString(j));
                    if (object.has(i + 1 + "")) {
                        if (object.get((i + 1) + "") != null) {
                            JSONObject timerNode = new JSONObject(object.getString((i + 1) + ""));

                            String startTimer =timerNode.getString("start");

                            mNodeViews.get(i).setStartTimer(startTimer);



                            String[] split = startTimer.split(":");
                            Integer[] integers ={Integer.valueOf(split[0]),Integer.valueOf(split[1])};
                            Calendar calendar =Calendar.getInstance();

                            calendar.set(Calendar.HOUR_OF_DAY,integers[0]);
                            calendar.set(Calendar.MINUTE,integers[1]);
                            calendar.add(Calendar.MINUTE,oneClassTimerLong);
                            String endTimer =simpleDateFormat.format(calendar.getTime());

                      //      String  endTimer=calenda//r.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
                            mNodeViews.get(i).setEndTimer(endTimer);


                        } else {
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onTimerModif() {
        loadNodeTimer();
    }

    @Override
    public void onTimerDelete(int index) {
        deleteNodeForIndex(index);
        loadNodeTimer();
    }

    @Override
    public boolean canScrollVertically(int direction) {
        Log.i("TAG3", "canScrollVertically: "+direction);
        if (direction < 0) {
            Log.i("TAG3", "canScrollVertically:getScaleY "+getScrollY());
            return getScrollY() != 0;

        }
        return !(getScrollY() + getHeight() >= computeVerticalScrollRange());
    }
}
