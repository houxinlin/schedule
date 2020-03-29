package com.hxl.course.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.hxl.course.R;
import com.hxl.course.entity.ClassEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClassInfoView extends AppCompatTextView {
    private static final String TAG = "TAG";
    private int mState;
    private Paint mDefaultPaint;

    private List<Integer> mColors;

    private int mDefaultClassHeight = 0;

    public ClassInfoView(Context context) {
        super(context);
        init();
    }

    public ClassInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mDefaultPaint = new Paint();
        int color = getResources().getColor(R.color.classAddStateColor);
        setBackgroundColor(color);
        mColors =new ArrayList<>();

        mColors.add(getResources().getColor(R.color.randColor1));
        mColors.add(getResources().getColor(R.color.randColor2));
        mColors.add(getResources().getColor(R.color.randColor3));
        mColors.add(getResources().getColor(R.color.randColor4));
        mColors.add(getResources().getColor(R.color.randColor5));
        mColors.add(getResources().getColor(R.color.randColor6));
    }

    public void setClassEntityInfo(ClassEntity entityInfo) {
        mState = 1;
        mDefaultClassHeight = (int) getResources().getDimension(R.dimen.classScheduleNodeHeight);

        setGravity(Gravity.CENTER);
        setBackgroundColor(Color.RED);
        setPadding(10, 20, 10, 20);

        setBackgroundColor(mColors.get(new Random().nextInt(mColors.size())));
        setTextColor(Color.WHITE);
        setTextSize(12);
        setText(entityInfo.getClassNamae());
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mState == 0) {
            Bitmap bitmap = getBitmapFromVectorDrawable(getContext(), R.drawable.ic_add);
            canvas.drawBitmap(bitmap, (getWidth() / 2) - (bitmap.getWidth() / 2),
                    getHeight() / 2 - bitmap.getHeight() / 2, mDefaultPaint);
        }


    }

    private static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }
}
