package com.hxl.course.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {
    public static final String SP_NAME="class";
    public Context mContext;
    private static  SpUtils mSpUtils;

    public synchronized static SpUtils getInstance() {
        if (mSpUtils==null){
            mSpUtils =new SpUtils();
        }
        return mSpUtils ;
    }
    private  SharedPreferences getSharedPreferences(){
       return mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }
    public Integer getInteger(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
       return sharedPreferences.getInt(key,-1);
    }
    public String getString(String key){
      return   getString(key,"");
    }
    public String getString(String key,String def){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,def);
    }
    public void setInteger(String key,Integer value){
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.putInt(key,value);
        edit.commit();
    }
    public void setString(String key,String value){
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.putString(key,value);
        edit.commit();
    }
    public void setContext(Context context) {
        mContext = context;
    }
}
