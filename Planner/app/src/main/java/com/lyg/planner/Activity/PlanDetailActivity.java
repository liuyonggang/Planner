package com.lyg.planner.Activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lyg.planner.BaseActivity;
import com.lyg.planner.R;
import com.lyg.planner.dao.PlanDao;
import com.lyg.planner.dao.SubPlanDao;
import com.lyg.planner.model.Plan;
import com.lyg.planner.model.SubPlan;
import com.lyg.planner.util.DatabaseHelper;

import java.util.List;

/**
 * Created by Administrator on 2016/3/18.
 */
public class PlanDetailActivity extends BaseActivity {

    private TextView subplanTextView,planProgressDisplay;
    private AppCompatSeekBar planSeekBar;
    private Plan plan;
    private SQLiteDatabase db;
    private PlanDao planDao;
    private SubPlanDao subPlanDao;
    private SubPlan mSubPlan;
    private List<SubPlan> subPlans;
    private int parentID = 0;
    //计划进度展示
    private LinearLayout totalProgressLayout;
    private TextView totalProgressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);

        getDB();
        setBar();
        changePlanProgress();
    }
    private void getDB(){
        plan = (Plan)this.getIntent().getSerializableExtra("plan");
        try{
            mSubPlan = (SubPlan)this.getIntent().getSerializableExtra("subplan");
        }catch (Exception e){

        }
        //init db
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        planDao = new PlanDao(db);
        subPlanDao = new SubPlanDao(db);
    }

    private void changePlanProgress() {
        parentID = plan.getProjectID();
        planProgressDisplay = (TextView)findViewById(R.id.plan_detail_progress);
        subplanTextView = (TextView)findViewById(R.id.plan_detail_subplan);
        planSeekBar = (AppCompatSeekBar)findViewById(R.id.plan_progress_seekbar);
        planProgressDisplay.setTypeface(getFZXiYuanFont());

        totalProgressLayout = (LinearLayout)findViewById(R.id.parent_progress_layout);
        totalProgressTextView = (TextView)findViewById(R.id.plan_detail_parentProgress);
        totalProgressTextView.setTypeface(getFZXiYuanFont());

        if (planDao.exists(parentID)){
            planProgressDisplay.setText(plan.getProgress() + "");
            Log.e("plan.getProgress",plan.getProgress()+"");
            planSeekBar.setProgress(plan.getProgress());
        }
        if (mSubPlan != null){
            totalProgressLayout.setVisibility(View.VISIBLE);
            totalProgressTextView.setText(plan.getProgress() + "");

            subplanTextView.setText(mSubPlan.getId()+"."+mSubPlan.getContent());
            planProgressDisplay.setText(mSubPlan.getProgress() + "");
            planSeekBar.setProgress(mSubPlan.getProgress());
        }

        planSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                planProgressDisplay.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                 saveProgress(seekBar.getProgress());
            }
        });
    }
    private void saveProgress(int progress){
        try {
            if (mSubPlan != null) {
                mSubPlan.setProgress(progress);
                if (subPlanDao.save(mSubPlan) < 0) {
                    throw new Exception("保存进度失败");
                }
                List<SubPlan> newSubPlans = subPlanDao.findAllByParentID(parentID);
                int parentProgress = 0;
                for (int i=0;i<newSubPlans.size(); i++){
                    SubPlan newSubPlan = newSubPlans.get(i);
                    int pro = newSubPlan.getProgress();
                    if (pro == 100){
                        parentProgress += newSubPlan.getWeight();
                    }
                    if (pro>0 && pro < 100){
                        parentProgress += Math.floor((double)(newSubPlan.getWeight()*pro/100));
                    }
                }
                Log.e("parentProgress",parentProgress+"<>");
                plan.setProgress(parentProgress);
                if (planDao.save(plan) < 0) {
                    throw new Exception("保存进度失败");
                }
                totalProgressTextView.setText(parentProgress + "");
            } else {
                plan.setProgress(progress);
                if (planDao.save(plan) < 0) {
                    throw new Exception("保存进度失败");
                }
            }

        } catch (Exception e) {
            toast("保存进度失败");
        }
    }
    private void setBar() {
        // Show the Up button in the action bar.
       /*ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/

        Toolbar toolbar = (Toolbar)this.findViewById(R.id.plan_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("计划详情");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout)findViewById(R.id.plan_detail_collapsing_toolbar);
        collapsingToolbar.setTitle(plan.getName());

    }


/*    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home){
            //navigateUpTo(new Intent(this,MainActivity.class));
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
