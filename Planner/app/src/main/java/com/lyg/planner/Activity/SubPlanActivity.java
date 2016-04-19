package com.lyg.planner.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lyg.planner.BaseActivity;
import com.lyg.planner.R;
import com.lyg.planner.adapter.SubPlanAdapter;
import com.lyg.planner.model.SubPlan;
import com.lyg.planner.util.AppUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 分解计划
 */
public class SubPlanActivity extends BaseActivity implements View.OnClickListener{

    private RecyclerView subPlanList;
    private SubPlanAdapter subPlanAdapter;
    private FloatingActionButton addPlanFB;
    private LinearLayout popManagerLayout;
    private Animation bottomIn,bottomOut;
    private View transView;
    /**
     * add sub plan
     */
    private TextInputLayout editInputLayout;
    private EditText subPlanEdit;
    private TextView subStartDate,subEndDate,seekPro;
    private SeekBar subPlanSeekBar;
    private LinearLayout addLayout,deleteLayout;
    /**
     * 存储数据的变量
     */
    private String startDate,endDate;
    private long startMilliTime,endMilliTime;
    private DateFormat formater = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private int year,month,day,hour,min;
    private int planProgress = 0;
    ArrayList<SubPlan> subPlans = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subplan);
        setBar();
        /**
         * break down plan
         */
        initSubPlan();
        /**
         * add sub plan
         */
        addSubPlan();
    }

    private void initSubPlan() {
        subPlanList = (RecyclerView)findViewById(R.id.sub_plan_list);
        subPlanList.setLayoutManager(new LinearLayoutManager(this));
        subPlanAdapter = new SubPlanAdapter(this,subPlans);
        subPlanList.setAdapter(subPlanAdapter);

        addPlanFB = (FloatingActionButton)findViewById(R.id.subplan_add_fb);
        addPlanFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popManagerLayout.startAnimation(bottomIn);
                popManagerLayout.setVisibility(View.VISIBLE);
                transView.setVisibility(View.VISIBLE);
            }
        });
        //仿PopupWindow
        bottomIn = AnimationUtils.loadAnimation(this,R.anim.popup_bottom_in);
        bottomOut = AnimationUtils.loadAnimation(this,R.anim.popup_bottom_out);
        popManagerLayout = (LinearLayout)findViewById(R.id.popup_manager_layout);
        popManagerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        transView = (View)findViewById(R.id.pop_trans_view);
        transView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popManagerLayout.startAnimation(bottomOut);
                popManagerLayout.setVisibility(View.GONE);
                transView.setVisibility(View.GONE);
            }
        });

        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);

    }
    private void addSubPlan() {
        //数据添加
        editInputLayout = (TextInputLayout)findViewById(R.id.subplan_content_layout);
        subPlanEdit = (EditText)findViewById(R.id.subplan_content);

        subStartDate = (TextView)findViewById(R.id.subplan_starttime);
        subEndDate= (TextView)findViewById(R.id.subplan_endtime);
        subStartDate.setOnClickListener(this);
        subEndDate.setOnClickListener(this);

        subPlanSeekBar = (SeekBar)findViewById(R.id.subplan_progress_seekbar);
        seekPro = (TextView)findViewById(R.id.subplan_detail_progress);
        seekPro.setTypeface(getFZXiYuanFont());
        seekPro.setText(0 + "");
        subPlanSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekPro.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                planProgress = seekBar.getProgress();
            }
        });

        //操作按钮
        addLayout = (LinearLayout)findViewById(R.id.add_sub_plan_done);
        deleteLayout = (LinearLayout)findViewById(R.id.delete_sub_plan);
        addLayout.setOnClickListener(this);
        deleteLayout.setOnClickListener(this);
    }
    private void setBar() {
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home){
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.subplan_starttime:
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        startDate = year + "/"+(monthOfYear+1)+"/"+dayOfMonth;
                        try{
                            subStartDate.setText(formater.format(formater.parse(startDate)));
                            startMilliTime = formater.parse(startDate).getTime();
                        }catch (ParseException e){

                        }

                        new TimePickerDialog(SubPlanActivity.this, new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int min) {
                                startDate = startDate+" "+ hour+":"+min;
                                try{
                                    subStartDate.setText(formater.format(formater.parse(startDate)));
                                    startMilliTime = formater.parse(startDate).getTime();
                                }catch (ParseException e){

                                }
                            }
                        },hour,min,true).show();
                        //startDT = new DateTime().withDate(year,monthOfYear+1,dayOfMonth).withTimeAtStartOfDay();
                        // projectStartTime.setText(startDT.toString(DateTimeFormat.forPattern("yyyy/MM/dd")));
                    }
                },year,month,day).show();
                break;
            case R.id.subplan_endtime:
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        endDate = year + "/"+(monthOfYear+1)+"/"+dayOfMonth;
                        try{
                            subEndDate.setText(formater.format(formater.parse(endDate)));
                            endMilliTime = formater.parse(endDate).getTime();
                        }catch (ParseException e){

                        }

                        new TimePickerDialog(SubPlanActivity.this, new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int min) {
                                endDate = endDate+" "+ hour+":"+min;
                                try{
                                    subEndDate.setText(formater.format(formater.parse(endDate)));
                                    endMilliTime = formater.parse(endDate).getTime();
                                }catch (ParseException e){

                                }
                            }
                        },hour,min,true).show();
                        //startDT = new DateTime().withDate(year,monthOfYear+1,dayOfMonth).withTimeAtStartOfDay();
                        // projectStartTime.setText(startDT.toString(DateTimeFormat.forPattern("yyyy/MM/dd")));
                    }
                },year,month,day).show();
                break;
            case R.id.add_sub_plan_done:

                if (!AppUtil.isEntityString(subPlanEdit.getText().toString())){
                    editInputLayout.setError("请输入计划");
                    return;
                }
                if (planProgress == 0){
                    toast("请设置进度");
                    return;
                }
                SubPlan subPlan = new SubPlan();
                subPlan.setContent(subPlanEdit.getText().toString());
                subPlan.setProgress(planProgress);
                subPlan.setStartDateMilli(startMilliTime);
                subPlan.setEndDateMilli(endMilliTime);
                //subPlan.setNumber();
                subPlans.add(subPlan);
                subPlanAdapter.notifyDataSetChanged();

                popManagerLayout.startAnimation(bottomOut);
                popManagerLayout.setVisibility(View.GONE);
                transView.setVisibility(View.GONE);
                break;
            case R.id.delete_sub_plan:
                break;
        }
    }
}
