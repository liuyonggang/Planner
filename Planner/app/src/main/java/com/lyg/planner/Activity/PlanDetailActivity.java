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
import android.widget.SeekBar;
import android.widget.TextView;

import com.lyg.planner.BaseActivity;
import com.lyg.planner.R;
import com.lyg.planner.dao.PlanDao;
import com.lyg.planner.model.Plan;
import com.lyg.planner.util.DatabaseHelper;

/**
 * Created by Administrator on 2016/3/18.
 */
public class PlanDetailActivity extends BaseActivity {

    private TextView planProgressDisplay;
    private AppCompatSeekBar planSeekBar;
    private Plan plan;
    private SQLiteDatabase db;
    private PlanDao planDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);

        setBar();
        changePlanProgress();
    }

    private void changePlanProgress() {

        plan = (Plan)this.getIntent().getSerializableExtra("plan");
        //init db
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        planDao = new PlanDao(db);

        planProgressDisplay = (TextView)findViewById(R.id.plan_detail_progress);
        planSeekBar = (AppCompatSeekBar)findViewById(R.id.plan_progress_seekbar);
        planProgressDisplay.setTypeface(getFZXiYuanFont());

        if (planDao.exists(plan.getProjectID())){
            planProgressDisplay.setText(plan.getProgress() + "");
            Log.e("plan.getProgress",plan.getProgress()+"");
            planSeekBar.setProgress(plan.getProgress());
        }

        planSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                planProgressDisplay.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    plan.setProgress(seekBar.getProgress());
                    if (planDao.save(plan) < 0) {
                        throw new Exception("could not save Task");
                    }
                } catch (Exception e) {
                    toast("保存失败");
                }
            }
        });
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
        collapsingToolbar.setTitle("计划详情计划详情计划详情计划详情");

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
