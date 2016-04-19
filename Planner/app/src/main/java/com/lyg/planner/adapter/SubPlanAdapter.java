package com.lyg.planner.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyg.planner.Activity.PlanDetailActivity;
import com.lyg.planner.NewPlanActivity;
import com.lyg.planner.R;
import com.lyg.planner.dao.PlanDao;
import com.lyg.planner.model.Plan;
import com.lyg.planner.model.SubPlan;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/14.
 */
public class SubPlanAdapter extends Adapter{

    Context mContext;
    ArrayList<SubPlan> subPlans;
    public SubPlanAdapter(Context context, ArrayList<SubPlan> subPlans){
        super(context);
        this.mContext = context;
        this.subPlans = subPlans;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_plan_add,parent,false);
        RecyclerView.ViewHolder  holder = new SubPlanHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SubPlan subPlan = subPlans.get(position);
        SubPlanHolder subPlanHolder = (SubPlanHolder)holder;
        subPlanHolder.subPlanNo.setText(position+1+"");
        subPlanHolder.subPlanContent.setText(subPlan.getContent());
        subPlanHolder.subPlanWeight.setText(subPlan.getProgress()+"");
    }

    @Override
    public int getItemCount() {
        return subPlans.size();
    }

    class SubPlanHolder extends RecyclerView.ViewHolder {
        TextView subPlanNo,subPlanContent,subPlanStartDate,subPlanEndDate,subPlanArrowIc,subPlanWeight;
        public SubPlanHolder(View itemView) {
            super(itemView);
            subPlanNo = (TextView)itemView.findViewById(R.id.sub_plan_No);
            subPlanContent = (TextView)itemView.findViewById(R.id.subplan_title);
            subPlanStartDate = (TextView)itemView.findViewById(R.id.subproject_startdate);
            subPlanStartDate.setTypeface(xiyuanFont);
            subPlanEndDate = (TextView)itemView.findViewById(R.id.subproject_enddate);
            subPlanEndDate.setTypeface(xiyuanFont);
            subPlanArrowIc = (TextView)itemView.findViewById(R.id.subproject_arrowdate_icon);
            subPlanArrowIc.setTypeface(iconFont);
            subPlanWeight = (TextView)itemView.findViewById(R.id.subplan_percent);
            subPlanWeight.setTypeface(xiyuanFont);
        }
    }
}
