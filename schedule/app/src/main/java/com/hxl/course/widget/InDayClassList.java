package com.hxl.course.widget;

import android.content.Context;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hxl.course.R;
import com.hxl.course.common.IDayClassListEvents;
import com.hxl.course.dialog.ClassEditDialog;
import com.hxl.course.entity.ClassEntity;
import com.hxl.course.storage.CourseStorage;
import com.hxl.course.utils.DensityUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.VIBRATOR_SERVICE;

public class InDayClassList extends LinearLayout implements ClassEditDialog.OnClick {
    private List<ClassEntity> mDayClassList;
    private int mPaddingSize;
    private static final String TAG = "InDayClassList";
    private int mDefaultClassHeight = 0;
    private boolean isMove = false;
    private long mDownTimer;
    private ClassEntity mCurrentDownClassEntity;
    private int mFirstIndex = 0;
    private int mDownIndexInClassEntity;
    private IDayClassListEvents mIDayClassListEvents;
    private  int mMoveSize=0;
    private  int mWeek;

    public InDayClassList(Context context, int week) {
        super(context);
        mWeek = week;
        initView();
    }

    public void setIDayClassListEvents(IDayClassListEvents IDayClassListEvents) {
        mIDayClassListEvents = IDayClassListEvents;
    }


    public InDayClassList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public InDayClassList(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < mDayClassList.size(); i++) {
            View childAt = getChildAt(i);
            ClassEntity classEntity = mDayClassList.get(i);
            int top = (classEntity.getStartNode() * mDefaultClassHeight) - mDefaultClassHeight;
            int bottom = (classEntity.getEndNode() * mDefaultClassHeight);
            childAt.layout(mPaddingSize, top + 10, getMeasuredWidth() - mPaddingSize, bottom);
        }
    }


    private void initView() {
        mDayClassList = new ArrayList<>();
        mDefaultClassHeight = (int) getResources().getDimension(R.dimen.classScheduleNodeHeight);
        mPaddingSize = DensityUtil.dip2px(getContext(), 2);
        setPadding(mPaddingSize, mPaddingSize, mPaddingSize, mPaddingSize);
        post(new Runnable() {
            @Override
            public void run() {
                loadWeekClass();
            }
        });
    }

    public void addClassView(int start, int end) {
        ClassEntity classEntity = new ClassEntity(start, end, "", "");
        addClassView(classEntity,0);

    }
    public void addClassView(ClassEntity newClass,int state) {
        ClassEntity classEntity = new ClassEntity(newClass.getStartNode(), newClass.getEndNode(), newClass.getClassNamae(), "");
        classEntity.setState(state);
        mDayClassList.add(classEntity);
        ClassInfoView classInfoView = new ClassInfoView(getContext());
        if (state==1){classInfoView.setClassEntityInfo(classEntity);}
        setViewNewLayoutParams(classInfoView, classEntity.getStartNode(), classEntity.getEndNode());
        addView(classInfoView);
        requestLayout();

    }

    private void setViewNewLayoutParams(ClassInfoView layoutParams, int start, int end) {
        int height = (mDefaultClassHeight * (end - start) + mDefaultClassHeight);
        LayoutParams params = new LayoutParams(getWidth() - mPaddingSize * 2, height);
        layoutParams.setLayoutParams(params);
    }

    private ClassInfoView getClassInfoViewIndexForObj(ClassEntity entity) {
        return (ClassInfoView) getChildAt(mDayClassList.indexOf(entity));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: InDayClassList事件-----》 " + event.getAction());
        int index = ((int) Math.floor(event.getY() / mDefaultClassHeight)) + 1;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mCurrentDownClassEntity = getDownIsContainClass(event.getY());
                mDownTimer = System.currentTimeMillis();
                if (mCurrentDownClassEntity != null) {
                    mFirstIndex = mCurrentDownClassEntity.getStartNode();
                    if (mCurrentDownClassEntity.getState()==0){
                        requestDisallowInterceptTouchEvent(true);
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                isMove = true;
                mMoveSize++;
                if (mCurrentDownClassEntity != null) {

                    if (index < mFirstIndex) {
                        if (!isOverflow(1, index)) {
                            mCurrentDownClassEntity.setStartNode(index);
                            mCurrentDownClassEntity.setEndNode(mFirstIndex);
                        }

                    } else {
                        if (!isOverflow(-1, index)) {
                            mCurrentDownClassEntity.setStartNode(mFirstIndex);
                            mCurrentDownClassEntity.setEndNode(index);
                        } else {
                        }
                    }
                    setViewNewLayoutParams(getClassInfoViewIndexForObj(mCurrentDownClassEntity),
                            mCurrentDownClassEntity.getStartNode(), mCurrentDownClassEntity.getEndNode());
                    requestLayout();

                }

                return false;
            case MotionEvent.ACTION_UP:
                //获取点击的表格ClassEntity

                //如果不为空并且没有发送移动 
                if (mCurrentDownClassEntity != null && (!isMove || mMoveSize<5) ) {
                    showClassEditDialog();
                    return true;
                }
                //如果没有移动
                if (!isMove || mMoveSize<5 && (mCurrentDownClassEntity==null)) {
                    //清除所有选择
                    mIDayClassListEvents.clearSelect();
                    //添加新的选择
                    addClassView(index, index);
                }
                mFirstIndex = 0;
                isMove = false;
                mMoveSize=0;
                return true;

        }

        return true;
    }

    private boolean isOverflow(int direction, int index) {
        int[] oldMap = new int[13];
        int[] newMap = new int[13];
        for (int i = 1; i < oldMap.length; i++) {
            oldMap[i] = findExist(i);
        }
        int start = direction < 0 ? mCurrentDownClassEntity.getStartNode() : index;
        int end = direction < 0 ? index : mCurrentDownClassEntity.getEndNode();

        for (int i = 1; i < newMap.length; i++) {
            if (i >= start && i <= end) {
                newMap[i] = 1;
            }
        }
        for (int i = 1; i < oldMap.length; i++) {
            if (newMap[i] == 1 && oldMap[i] == 1) {
                return true;
            }
        }
        return false;
    }

    private int findExist(int node) {
        for (int i = 0; i < mDayClassList.size(); i++) {
            if (node >= mDayClassList.get(i).getStartNode() &&
                    node <= mDayClassList.get(i).getEndNode() && mDayClassList.get(i).getState() != 0) {
                return 1;
            }
        }
        return 0;
    }

    private void showClassEditDialog() {
        ClassEditDialog classEditDialog = new ClassEditDialog(getContext(), R.style.EditDialog);
        classEditDialog.setOnClick(this);
        classEditDialog.show();
    }

    private ClassEntity getDownIsContainClass(float y) {
        int index = ((int) Math.floor(y / mDefaultClassHeight)) + 1;
        for (int i = 0; i < mDayClassList.size(); i++) {
            if (index >= mDayClassList.get(i).getStartNode() && index <= mDayClassList.get(i).getEndNode()) {
                return mDayClassList.get(i);
            }
        }
        return null;
    }

    public void clearAddStateView() {
        int index = 0;

        Iterator<ClassEntity> iterator = mDayClassList.iterator();
        while (iterator.hasNext()) {
            ClassEntity next = iterator.next();
            if (next.getState() == 0) {
                removeViewAt(index);
                Log.i(TAG, "clearAddStateView: " + index);
                iterator.remove();
            }
            index++;
        }
        requestLayout();
    }

    @Override
    public void onClick(String name) {
        if (mCurrentDownClassEntity != null) {
            mCurrentDownClassEntity.setState(1);
            mCurrentDownClassEntity.setClassNamae(name);
            int i = mDayClassList.indexOf(mCurrentDownClassEntity);
            ClassInfoView childAt = (ClassInfoView) getChildAt(i);
            childAt.setClassEntityInfo(mCurrentDownClassEntity);
            saveClassInfo();
        }

    }

    @Override
    public void onDelete() {
        int i = mDayClassList.indexOf(mCurrentDownClassEntity);
        mDayClassList.remove(i);
        removeViewAt(i);
        requestLayout();
        saveClassInfo();
    }

    /**
     * 保存到本地
     *
     */
    private  void saveClassInfo(){
        CourseStorage storage =new CourseStorage();
        storage.setValue(mWeek,mDayClassList);
    }


    private void loadWeekClass(){
        CourseStorage storage =new CourseStorage();
        List<ClassEntity> weekList = storage.getValue(mWeek, new ArrayList<ClassEntity>());
        for (int i = 0; i < weekList.size(); i++) {
            addClassView(weekList.get(i),1);
        }
    }


}
