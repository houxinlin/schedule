package com.hxl.course.widget.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ViewRelation  {

    private View  mTopView;
    private View  mDownView;
    private View  mLeftView;
    private View  mRightView;
    private  View mView;

    public View getTopView() {
        return mTopView;
    }

    public void setTopView(View topView) {
        mTopView = topView;
        topView.setTag("topView");
    }

    public View getDownView() {
        return mDownView;
    }

    public void setDownView(View downView) {
        mDownView = downView;
        downView.setTag("downView");
    }

    public View getLeftView() {
        return mLeftView;
    }

    public void setLeftView(View leftView) {
        mLeftView = leftView;
        leftView.setTag("leftView");
    }

    public View getRightView() {
        return mRightView;
    }

    public void setRightView(View rightView) {
        mRightView = rightView;
        rightView.setTag("rightView");
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
        view.setTag("view");
    }


}
