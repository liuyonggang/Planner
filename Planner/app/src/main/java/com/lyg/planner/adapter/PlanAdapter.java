package com.lyg.planner.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyg.planner.Activity.PlanDetailActivity;
import com.lyg.planner.NewPlanActivity;
import com.lyg.planner.R;
import com.lyg.planner.dao.BaseDao;
import com.lyg.planner.dao.PlanDao;
import com.lyg.planner.model.Plan;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
/**
 * Created by Administrator on 2016/3/5.
 */
public class PlanAdapter extends Adapter{

    public static final int ADD_PROJECT = 0;
    public static final int PROJECT_ITEM= 1;

    private Context mContext;
    private List<Plan> plans;
    private PlanDao planDao;
    private int[] colors = new int[]{R.color.orange_red,R.color.light_blue_green,R.color.orange_yellow,R.color.blue_sky};
    private int year,month,day,hour,min;
    private DateTime currentDT;

    public PlanAdapter(Context context,List<Plan> projects,PlanDao daoObj){
        super(context,projects,daoObj);
        this.mContext = context;
        this.plans = projects;
        this.planDao = daoObj;

        /*Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);

        currentDT = new DateTime().withDate(year,month+1,day).withTimeAtStartOfDay();*/
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case PROJECT_ITEM:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan,parent,false);
                holder = new PlanViewHolder(v);
                break;
            case ADD_PROJECT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan_add,parent,false);
                holder = new AddProjectHolder(v);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Plan plan = plans.get(position);
        if (position == plans.size()-1){
            AddProjectHolder addProjectHolder = (AddProjectHolder)holder;
            addProjectHolder.addLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,NewPlanActivity.class);
                    plan.setProjectID(planDao.getLastID()+1);
                    i.putExtra("plan",plan);
                    v.getContext().startActivity(i);
                }
            });
        }else {
            PlanViewHolder projectViewHolder = (PlanViewHolder)holder;
            projectViewHolder.projectBaseLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,PlanDetailActivity.class);
                    i.putExtra("plan",plan);
                    v.getContext().startActivity(i);
                }
            });
            projectViewHolder.projectBaseLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent i = new Intent(context,NewPlanActivity.class);
                    i.putExtra("plan",plan);
                    v.getContext().startActivity(i, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
                    return true;
                }
            });
            projectViewHolder.projectContent.setText(plans.get(position).getName());
            projectViewHolder.aothText.setText(plans.get(position).getGoal());

            DateTime edt = new DateTime().withMillis(plans.get(position).getEndDate());
            projectViewHolder.projectEndDate.setText(edt.toString(DateTimeFormat.forPattern("yyyy/MM/dd")));

            //任务进度
            projectViewHolder.planProgress.setText(plan.getProgress() + "%");
            projectViewHolder.planProgressBar.setProgress(plan.getProgress());
            Log.e("Milli-Time",getMilliCurrentDate()+"<>"+plan.getEndDate());

            //时间进度
            if (getMilliCurrentDate() <= plan.getEndDate()){
                long totalDate = plan.getEndDate() - plan.getStartDate();
                long hasGoneDate = getMilliCurrentDate() - plan.getStartDate();

                BigDecimal bd1 = new BigDecimal(Long.toString(hasGoneDate));
                BigDecimal bd2 = new BigDecimal(Long.toString(totalDate));

                double newProgress = bd1.divide(bd2,2,BigDecimal.ROUND_HALF_UP).doubleValue();
                int finalProgress = (int)(newProgress*100);

                projectViewHolder.planDtProgressBar.setProgress(finalProgress);
                projectViewHolder.planDtProgress.setText(finalProgress + "%");
                projectViewHolder.projectBaseLayout.setCardBackgroundColor(mContext.getResources().getColor(R.color.blue_green));
            }else {
                projectViewHolder.planDtProgressBar.setProgress(100);
                projectViewHolder.planDtProgress.setText("100%");
                projectViewHolder.projectBaseLayout.setCardBackgroundColor(mContext.getResources().getColor(R.color.orange_red));
            }

        }
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public int getItemViewType(int position){
        if (position == plans.size() - 1){
            return ADD_PROJECT;
        } else {
            return PROJECT_ITEM;
        }
    }

    class  PlanViewHolder extends RecyclerView.ViewHolder{

        TextView aothText,projectContent,projectArrowDateIcon,projectEndDate,projectStatus
                ,projectStartDate,subPlanIc,subPlanName;

        android.support.v7.widget.CardView projectBaseLayout;
        ProgressBar planProgressBar,planDtProgressBar;
        TextView planProgress,planDtProgress;
        RelativeLayout subPlanLayout;

        public PlanViewHolder(View view){
            super(view);
            //誓言
            aothText = (TextView)view.findViewById(R.id.project_oath);
            aothText.setTypeface(xiyuanFont);

            //计划内容
            projectContent = (TextView)view.findViewById(R.id.project_content);
            //projectContent.setTypeface(getFZShangPinFont());

            //计划到期时间
            projectArrowDateIcon = (TextView)view.findViewById(R.id.project_arrowdate_icon);
            projectArrowDateIcon.setTypeface(iconFont);
            projectEndDate = (TextView)view.findViewById(R.id.project_enddate);
            projectEndDate.setTypeface(xiyuanFont);
            projectStartDate = (TextView)view.findViewById(R.id.project_startdate);
            projectStartDate.setTypeface(xiyuanFont);

            //项目状态
            projectStatus = (TextView)view.findViewById(R.id.project_status_icon);
            projectStatus.setTypeface(iconFont);

            //item背景
            projectBaseLayout = (android.support.v7.widget.CardView)view.findViewById(R.id.project_item_layout);
           // projectBaseLayout.setCardBackgroundColor(mContext.getResources().getColor(colors[new Random().nextInt(colors.length)]));

            //展示子任务
            subPlanLayout = (RelativeLayout)view.findViewById(R.id.sub_plan_layout);
            subPlanLayout.setVisibility(View.GONE);
            subPlanIc = (TextView)view.findViewById(R.id.sub_plan_ic);
            subPlanIc.setTypeface(newIcFont);
            subPlanName = (TextView)view.findViewById(R.id.sub_plan_display);

            //任务和时间进度条
            planProgressBar = (ProgressBar)view.findViewById(R.id.plan_progressbar);
            planDtProgressBar = (ProgressBar)view.findViewById(R.id.plan_time_progressbar);
            planProgress = (TextView)view.findViewById(R.id.plan_item_progress);
            planDtProgress = (TextView)view.findViewById(R.id.plan_time_progress);
        }
    }
    class AddProjectHolder extends RecyclerView.ViewHolder{
        CardView addLayout;
        TextView addIc;

        public AddProjectHolder(View view){
            super(view);
            addIc = (TextView)view.findViewById(R.id.project_add_icon);
            addIc.setTypeface(iconFont);

            addLayout = (CardView)view.findViewById(R.id.project_item_add_layout);
        }
    }

    public long getMilliCurrentDate(){
        //return currentDT.getMillis();
        return Calendar.getInstance().getTimeInMillis();
    }
}
