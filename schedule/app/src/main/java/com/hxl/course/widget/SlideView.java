package com.hxl.course.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

import com.hxl.course.R;

public class SlideView extends CenterTextView {
    private  float mCurrentValue;
    private Paint mSelectPaint;
    private int mRadius=100;
    private int mSelectColor;

    private  int mAnimatorDuration=500;

    private  int mMaxValue =20;

    public SlideView(Context context) {
        this(context,null);

    }

    public SlideView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }



    public SlideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlideView);
        mRadius =(int) ta.getDimension(R.styleable.SlideView_radius,100);
        mSelectColor=ta.getColor(R.styleable.SlideView_selectColor,Color.parseColor("#F5F5F5"));
        mMaxValue=ta.getInt(R.styleable.SlideView_maxValue,100);
        ta.recycle();
        init();
    }

    @Override
    public void init() {
        super.init();
        mSelectPaint = new Paint();
        mSelectPaint.setColor(mSelectColor);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction()==MotionEvent.ACTION_UP) {
            if (event.getX()>0 && event.getX()<=getWidth())
            mCurrentValue = ((int) event.getX());

        }
            invalidate();
        return true;
    }

    @Override
    public void draw(Canvas canvas) {

        Path path = new Path();
        path.addRoundRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mRadius, mRadius, Path.Direction.CW);
        canvas.clipPath(path);
        super.draw(canvas);

    }

    public  int getCurrentValue() {
        return Math.round(mCurrentValue/getWidth()*mMaxValue);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, mCurrentValue, getMeasuredHeight(),  mSelectPaint);
        setText(converXToValue(mCurrentValue)+"");
        super.onDraw(canvas);

    }

    private  int   converXToValue(float x){
        return  Math.round(x/getWidth()*mMaxValue) ;
    }
    public void  setCurrentValue(int value){
        if (value>mMaxValue){return;}
        float oldX =mCurrentValue;
        float newX =value*getWidth()/mMaxValue;
        ValueAnimator animator =ValueAnimator.ofFloat(oldX,newX);
        animator.setDuration(mAnimatorDuration);
        animator.setInterpolator(new OvershootInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v =(float)animation.getAnimatedValue();
                mCurrentValue= v;
                invalidate();
            }
        });
        animator.start();

    }

    public void setRadius(int radius) {
        mRadius = radius;
        invalidate();
    }

    public void setAnimatorDuration(int animatorDuration) {
        mAnimatorDuration = animatorDuration;
    }
}
