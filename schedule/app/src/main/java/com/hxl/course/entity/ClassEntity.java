package com.hxl.course.entity;

import java.util.List;

public class ClassEntity {
    private int mStartNode;
    private int mEndNode;
    private String mClassNamae;
    private String mTeacherName;
    private int mState;

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        mState = state;
    }

    public ClassEntity(int startNode, int endNode, String classNamae, String teacherName) {
        mStartNode = startNode;
        mEndNode = endNode;
        mClassNamae = classNamae;
        mTeacherName = teacherName;
    }

    public int getStartNode() {
        return mStartNode;
    }

    public int getEndNode() {
        return mEndNode;
    }

    public String getClassNamae() {
        return mClassNamae;
    }

    public String getTeacherName() {
        return mTeacherName;
    }

    public void setStartNode(int startNode) {
        mStartNode = startNode;
    }

    public void setEndNode(int endNode) {
        mEndNode = endNode;
    }

    public void setClassNamae(String classNamae) {
        mClassNamae = classNamae;
    }

    public void setTeacherName(String teacherName) {
        mTeacherName = teacherName;
    }
}
