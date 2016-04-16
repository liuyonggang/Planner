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

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2016/4/14.
 */
public class SubPlanAdapter extends Adapter{

    Context mContext;
    public SubPlanAdapter(Context context){
        super(context);
        this.mContext = context;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_plan_add,parent,false);
        RecyclerView.ViewHolder  holder = new SubPlanHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SubPlanHolder subPlanHolder = (SubPlanHolder)holder;
        subPlanHolder.subPlanNo.setText(position+1+"");
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class SubPlanHolder extends RecyclerView.ViewHolder {
        TextView subPlanNo;
        public SubPlanHolder(View itemView) {
            super(itemView);
            subPlanNo = (TextView)itemView.findViewById(R.id.sub_plan_No);
        }
    }
}
