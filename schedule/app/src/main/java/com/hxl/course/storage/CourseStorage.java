package com.hxl.course.storage;

import com.hxl.course.entity.ClassEntity;
import com.hxl.course.utils.SpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CourseStorage implements ISharedPreferencesStorage<Integer, List<ClassEntity>> {
    private  SpUtils mSpUtils =SpUtils.getInstance();

    @Override
    public String setValue(Integer key, List<ClassEntity> value) {
        try {
            JSONArray array =new JSONArray();
            for (int i = 0; i < value.size(); i++) {
                ClassEntity classEntity = value.get(i);
                JSONObject object =new JSONObject();
                object.put("start",classEntity.getStartNode());
                object.put("end",classEntity.getEndNode());
                object.put("name",classEntity.getClassNamae());
                array.put(object);
            }
            SpUtils.getInstance().setString("class_info_" + key,array.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public List<ClassEntity> getValue(Integer key, List<ClassEntity> defValue) {
        try {
            JSONArray weekJson;
            String week = SpUtils.getInstance().getString("class_info_" + key);
            if (week.length()==0){
                return defValue;
            }
            List<ClassEntity> result =new ArrayList<>();
            weekJson =new JSONArray(week);
            for (int i = 0; i < weekJson.length(); i++) {
                JSONObject jsonObject = weekJson.getJSONObject(i);
                ClassEntity entity =new ClassEntity(jsonObject.getInt("start"),
                        jsonObject.getInt("end"),jsonObject.getString("name"),"");
                entity.setState(1);
                result.add(entity);
            }
            return  result;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return defValue;
    }
}
