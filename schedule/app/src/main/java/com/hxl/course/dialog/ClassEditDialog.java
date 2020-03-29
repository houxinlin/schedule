package com.hxl.course.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hxl.course.R;

public class ClassEditDialog extends Dialog {
    private  OnClick mOnClick ;

    public OnClick getOnClick() {
        return mOnClick;
    }

    public void setOnClick(OnClick onClick) {
        mOnClick = onClick;
    }

    public ClassEditDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public ClassEditDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    public ClassEditDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }
    private  void init(){
        setContentView(R.layout.dialog_edit_class_info);
        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.et_name)).getText().toString();
                if (name.length()==0){
                    return;
                }
                if (mOnClick!=null){
                    mOnClick.onClick(name);
                }
                dismiss();

            }
        });
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClick!=null){
                    mOnClick.onDelete();
                }
                dismiss();
            }
        });
    }
    public  interface  OnClick{
        void onClick(String name);
        void onDelete();
    }
}
