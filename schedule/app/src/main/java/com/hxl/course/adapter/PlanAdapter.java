package com.hxl.course.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hxl.course.R;
import com.hxl.course.activity.PlanActivity;
import com.hxl.course.entity.PlanEntity;
import com.hxl.course.storage.PlanStorage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    private List<PlanEntity> mPlanEntities;
    private Context mContext;

    public PlanAdapter(List<PlanEntity> planEntities, Context context) {
        mPlanEntities = planEntities;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_plan, parent, false));
    }

    private void showOptMenu(View view,int postion){
        PopupMenu menu =new PopupMenu(mContext,view);
        menu.getMenu().add("删除");
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mPlanEntities.remove(postion);
                new PlanStorage().addAll(mPlanEntities);
                notifyDataSetChanged();
                Toast.makeText(mContext, "删除", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        menu.show();

    }
    private  void startEditActivity(int postion){


    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int hour =mPlanEntities.get(position).getStartTimer().get(Calendar.HOUR_OF_DAY);
        int minute =mPlanEntities.get(position).getStartTimer().get(Calendar.MINUTE);
        SimpleDateFormat format =new SimpleDateFormat("HH:mm");
        String show = format.format(mPlanEntities.get(position).getStartTimer().getTime());
        holder.mTimer.setText(show);
        holder.mDescribe.setText(mPlanEntities.get(position).getDescribe());
        holder.mPrant.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showOptMenu(v,position);
                return false;
            }
        });
        holder.mPrant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (mPlanEntities.size()>1 && position<mPlanEntities.size()-1){
            Calendar localTime = converLocalTime(mPlanEntities.get(position ).getStartTimer().get(Calendar.HOUR_OF_DAY),
                    mPlanEntities.get(position ).getStartTimer().get(Calendar.MINUTE));

            Calendar next = converLocalTime(mPlanEntities.get(position + 1).getStartTimer().get(Calendar.HOUR_OF_DAY),
                    mPlanEntities.get(position + 1).getStartTimer().get(Calendar.MINUTE));
            long until = next.getTimeInMillis()-localTime.getTimeInMillis();
            until=until/1000/60;
            String s="分钟";
            if (until>60){
                until=until/60;
                s="小时";
            }
            holder.mLong.setText(" ("+until +s +")");

        }

    }

    private  Calendar converLocalTime(int hour,int minute){
        Calendar calendar =Calendar.getInstance();

        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),hour,minute);
        return calendar;
    }
    @Override
    public int getItemCount() {
        return mPlanEntities.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder{
         TextView mTimer;
         TextView mDescribe;
        TextView mLong;
        ViewGroup mPrant;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTimer =itemView.findViewById(R.id.tv_timer);
            mDescribe=itemView.findViewById(R.id.tv_describe);
            mLong =itemView.findViewById(R.id.tv_long);
            mPrant=itemView.findViewById(R.id.prant);
        }
    }
}
