package com.hxl.course.storage;

import android.util.Log;

import com.hxl.course.entity.PlanEntity;
import com.hxl.course.utils.SpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlanStorage  implements ISharedPreferencesStorage<PlanEntity, PlanEntity>{
    public  void addAll(List<PlanEntity> planEntities){
         SpUtils.getInstance().setString("plan_list","");
        for (int i = 0; i < planEntities.size(); i++) {
            setValue(planEntities.get(i),planEntities.get(i));
        }
    }
    @Override
    public String setValue(PlanEntity key, PlanEntity value) {
        int hour =key.getStartTimer().get(Calendar.HOUR_OF_DAY);
        int minute =key.getStartTimer().get(Calendar.MINUTE);

        try {
            String planList = SpUtils.getInstance().getString("plan_list");
            JSONArray jsonArray ;
            if (planList.length()==0){
                jsonArray =new JSONArray();
            }else {
                jsonArray = new JSONArray(planList);
            }
            //如果起止时间有冲突
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = new JSONObject(jsonArray.getString(i));
                String timer = item.getString("mStartTimer");
                String[] split = timer.split(":");
                int[] converTimer = converTimer(split);
                if (converTimer[0]==hour && converTimer[1] ==minute){
                    return "记录存在";
                }
            }
            jsonArray.put(key.toString());
            SpUtils.getInstance().setString("plan_list",jsonArray.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "保存成功";
    }

    @Override
    public PlanEntity getValue(PlanEntity key, PlanEntity defValue) {

        return null;
    }

    @Override
    public List<PlanEntity> getList() {
        List<PlanEntity> result =new ArrayList<>();
        try {

            String planList = SpUtils.getInstance().getString("plan_list");
            JSONArray jsonArray ;
            if (planList.length()==0){
                return result;
            }else {
                jsonArray =new JSONArray(planList);
            }
            Log.i("TAG", "getList: jsonArray"+jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = new JSONObject(jsonArray.getString(i));
                String timer = item.getString("mStartTimer");
                int[] converTimer = converTimer(timer.split(":"));

                PlanEntity planEntity = new PlanEntity(gengCalender(converTimer[0],converTimer[1]),null,item.getString("mDescribe"));
                result.add(planEntity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private  int[] converTimer(String[] strings){
        return new int[]{Integer.valueOf(strings[0]),Integer.valueOf(strings[1])};
    }
    private  Calendar gengCalender(int hour,int minute){
        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        return calendar;
    }

}
