package com.hxl.course.widget.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import androidx.core.view.ViewConfigurationCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MulitViewGroup extends ViewGroup {
    private static final int MOVE_THRESHOLD = 8;
    private static final String TAG = "TAG2";
    private int mTouchSlop;
    private int mCurrentDirection = -1;


    private ViewRelation mViewRelation;
    private View mCurrentViewReleation;


    private float mDownX;
    private float mDownY;
    private int mScrollSize = 0;


    private int mScrollCount = 0;

    private Scroller mScroller = new Scroller(getContext());

    public MulitViewGroup(Context context) {
        super(context);
    }

    public MulitViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MulitViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        // 获取TouchSlop值
        mTouchSlop = configuration.getScaledTouchSlop();

        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        Log.i("TAG4", "initView: "+childCount);
        switch (childCount) {
            case 4:
                ViewRelation viewRelation = new ViewRelation();
                viewRelation.setView(getChildAt(0));
                viewRelation.setLeftView(getChildAt(1));
                viewRelation.setRightView(getChildAt(2));
                viewRelation.setDownView(getChildAt(3));
                mViewRelation = viewRelation;

                mCurrentViewReleation = viewRelation.getView();

                break;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int xSize = (int) (mDownX > event.getX() ? mDownX - event.getX() : event.getX() - mDownX);
            int ySize = (int) (mDownY > event.getY() ? mDownY - event.getY() : event.getY() - mDownY);


            if ((mCurrentDirection == -1 || mCurrentDirection == 0)) {
                mScrollSize += -(int) (event.getX() - mDownX);
                mCurrentDirection = 0;
                scrollBy(-(int) (event.getX() - mDownX), 0);
                mDownX = event.getX();
                mDownY = event.getY();
                return true;
            }

            if ((mCurrentDirection == -1 || mCurrentDirection == 1) && (mScrollCount <= 1 && mScrollCount >= -1)) {
                
                mScrollSize += -(int) (event.getY() - mDownY);
                mCurrentDirection = 1;
                scrollBy(0, -(int) (event.getY() - mDownY));
                mDownX = event.getX();
                mDownY = event.getY();

                return true;
            }


        }
        /**
         * 手指释放
         */
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.i(TAG, "onTouchEvent: 释放2");
            if (mCurrentDirection == 0 && mScrollSize != 0) {
                /**
                 *   手指向左滑动，
                 */

                if (mScrollSize > 0) {
                    if (getScrollX()+getWidth()>getWidth()*2){
                        int dx = mScrollCount * getWidth() - getScrollX();
                            mScroller.startScroll(getScrollX(),0,dx,0,500);
                    }else{
                        mScrollCount++;
                        int dx = mScrollCount * getWidth() - getScrollX();
                        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
                        mCurrentViewReleation = mViewRelation.getRightView();
                    }


                } else {
                    Log.i(TAG, "onTouchEvent: getScrollX"+getScrollX());
                    if (getScrollX() < -(getWidth())){
                        int dx = Math.abs(mScrollCount * getWidth() - getScrollX());
                        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
                    }else {
                        mScrollCount--;
                        int dx = Math.abs(mScrollCount * getWidth() - getScrollX());
                        mScroller.startScroll(getScrollX(), 0, -dx, 0, 500);
                        mCurrentViewReleation = mViewRelation.getLeftView();
                    }
//                    if (getScrollX()+getWidth()<=-(getWidth()*2)){
//
//                    }else {
//                        mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 500);
//                    }
                }

            } else if (mCurrentDirection == 1) {
                Log.i(TAG, "onTouchEvent: 大小" + mScrollSize);
                if (mScrollSize > 0) {

                    if ((getScrollY()+getHeight())>getHeight()*2){
                        Log.i(TAG, "onTouchEvent: yuejie");
                        int dy = mScrollCount * getHeight() - getScrollY();
                        mScroller.startScroll(0, getScrollY(), 0, dy, 500);
                    }else{
                        mScrollCount++;
                        int dy = mScrollCount * getHeight() - getScrollY();
                        mScroller.startScroll(0, getScrollY(), 0, dy, 500);
                        mCurrentViewReleation = mViewRelation.getDownView();
                    }

                } else {
                    if (getScrollY()>=0){
                        mScrollCount--;
                        int dy = Math.abs(mScrollCount * getHeight() - getScrollY());
                        mScroller.startScroll(0, getScrollY(), 0, -dy, 500);
                        mCurrentViewReleation = mViewRelation.getView();
                        /**
                         * 不允许向下滑动，否则归为
                         */
                    }else {
                        mScroller.startScroll(0, getScrollY(), 0, -getScrollY(), 500);
                    }


                }
            }
            invalidate();
            mScrollSize = 0;

            mCurrentDirection = -1;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //获取按下时候的位置
            mDownX = event.getX();
            mDownY = event.getY();
            return false;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE && mScroller.isFinished()) {
            int xSize = (int) (mDownX - event.getX());
            int ySize = (int) (mDownY - event.getY());
            //上下左右滑动都到达了阈值

            if (Math.abs(xSize) >= mTouchSlop || Math.abs(ySize) >= mTouchSlop) {

                /**
                 * 如果左右大小大于上下大小
                 */
                if (Math.abs(xSize) > Math.abs(ySize)) {
                    mCurrentDirection = 0;

                    if (getScrollY()!=0){
                        return false;
                    }else {

                    }


                } else {

                    /**
                     * 上下滑动
                     */
                    mCurrentDirection = 1;

                    if (getScrollX()!=0){

                        return false;
                    }
                    /**
                     * 如果是主View，判断主View是否可以滑动，如果可以则教给子view处理
                     */
                    Log.i("TAG4", "onInterceptTouchEvent移动2: "+mCurrentViewReleation.getTag());
                    if (mCurrentViewReleation==mViewRelation.getView() &&
                            findViewWithTag("ClassScheduleCardContent").canScrollVertically(ySize)){

                        return false;
                    }

                    if (mCurrentViewReleation==mViewRelation.getDownView()){

                        RecyclerView planRecycleView = findViewWithTag("PlanRecycleView");
                        Log.i(TAG, "onInterceptTouchEvent: 判读子视图是否可以滚动"+planRecycleView.canScrollVertically(ySize));
                        if (planRecycleView.canScrollVertically(ySize)){
                            return false;
                        }
                    }


                }
                return true;
            }else{

                Log.i(TAG, "onInterceptTouchEvent: 失败"+xSize +"  "+ySize +"  "+mTouchSlop);
            }
        }


        return false;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (mViewRelation != null) {
            if (mViewRelation.getView() != null) {
                mViewRelation.getView().layout(l, t, r, b);
            }
            if (mViewRelation.getRightView() != null) {
                mViewRelation.getRightView().layout(r, 0, r + r, b);
            }
            if (mViewRelation.getLeftView() != null) {
                mViewRelation.getLeftView().layout(-r, 0, 0, b);
            }
            if (mViewRelation.getDownView() != null) {
                Log.i(TAG, "onLayout: " + r + " " + l + "  " + b + "  " + t);
                mViewRelation.getDownView().layout(0, b, r, mViewRelation.getDownView().getMeasuredHeight()+b);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        int childCount = getChildCount();
        Log.i(TAG, "onMeasure: "+childCount);
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }

    }
}
