package com.lyg.planner;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lyg.planner.Activity.SubPlanActivity;
import com.lyg.planner.adapter.SubPlanAdapter;
import com.lyg.planner.dao.PlanDao;
import com.lyg.planner.model.Plan;
import com.lyg.planner.util.DatabaseHelper;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.IllegalFormatCodePointException;

/**
 * Created by Administrator on 2016/3/4.
 */
public class NewPlanActivity extends BaseActivity implements View.OnClickListener{

    private TextInputLayout contentLayout,goalLayout;
    private int year,month,day,hour,min;

    private EditText contentEditView,goalEditView,projectMemo;
    private Button btn;
    private TextView contentDes;
    private Spinner projectStatusSp,planPriority;
    private PlanDao planDao;
    private Plan plan;
    private SQLiteDatabase db;
    private DateTime startDT;
    private DateTime endDT;
    private TextView projectStartTime,projectEndTime;

    private FloatingActionButton completeBtn,deleteBtn,breakdownBtn;
    private ImageView icPriority;

    private boolean isEdit = false;
    private String startDate,endDate;
    private DateFormat formater = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private long startMilliTime,endMilliTime;

    private CardView addSubPlanLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newplan);

        /**
         * 设置标题栏
         */
        setBar();

        /**
         * 初始化
         */
        init();

        getDate();
        initDataFromDB();

        addSubPlanLayout = (CardView)findViewById(R.id.subplan_add);
        addSubPlanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forward(SubPlanActivity.class);
            }
        });
    }



    private void initDataFromDB() {
        plan = (Plan)getIntent().getSerializableExtra("plan");

        //db
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        planDao = new PlanDao(db);

        if (planDao.exists(plan.getProjectID())){
            isEdit = true;
            contentEditView.setText(plan.getName());
            goalEditView.setText(plan.getGoal());
            projectStatusSp.setSelection(plan.getStatus());
            planPriority.setSelection(plan.getPlanType());

            startDT =  new DateTime().withMillis(plan.getStartDate()).withTimeAtStartOfDay();
            projectStartTime.setText(startDT.toString(DateTimeFormat.forPattern("yyyy/MM/dd")));

            endDT =  new DateTime().withMillis(plan.getEndDate()).withTimeAtStartOfDay();
            projectEndTime.setText(endDT.toString(DateTimeFormat.forPattern("yyyy/MM/dd")));

            projectMemo.setText(plan.getMemo());
        }
    }

    private void init() {
        contentLayout = (TextInputLayout)findViewById(R.id.plan_content_layout);
        contentEditView = (EditText)findViewById(R.id.plan_content);
        goalEditView = (EditText)findViewById(R.id.plan_goal);
        projectMemo = (EditText)findViewById(R.id.plan_memo);
        contentDes = (TextView)findViewById(R.id.plan_content_description);

        projectStartTime = (TextView)findViewById(R.id.project_starttime);
        projectEndTime = (TextView)findViewById(R.id.project_endtime);

        projectStatusSp = (Spinner)findViewById(R.id.project_status);
        planPriority = (Spinner)findViewById(R.id.plan_priority);

        projectStartTime.setTypeface(getFZXiYuanFont());
        projectEndTime.setTypeface(getFZXiYuanFont());
        projectStartTime.setOnClickListener(this);
        projectEndTime.setOnClickListener(this);

        icPriority = (ImageView)findViewById(R.id.ic_priority);
        setDate();
        /**
         * 底部操作按钮
         */
        completeBtn = (FloatingActionButton)findViewById(R.id.plan_complete);
        deleteBtn  = (FloatingActionButton)findViewById(R.id.plan_delete);
        //breakdownBtn = (FloatingActionButton)findViewById(R.id.plan_breakdown);
        //breakdownBtn.setOnClickListener(this);
        completeBtn.setOnClickListener(this);
        //deleteBtn
        deleteBtn.setOnClickListener(this);
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);

        startDT = new DateTime().withDate(year,month+1,day).withTimeAtStartOfDay();
        startDate = year + "/"+(month+1)+"/"+day+" "+hour+":"+min;
        try{

            startMilliTime = formater.parse(startDate).getTime();
            Log.e("startMilliTime",startMilliTime+"<>"+startDate);
        }catch (ParseException e){
            Log.e("startMilliTime-Exp",e.getMessage()+"");
        }

        //projectStartTime.setText(startDT.toString(DateTimeFormat.forPattern("yyyy/MM/dd HH:mm")));
        projectStartTime.setText(formater.format(calendar.getTime()));

        //默认任务周期3天
        calendar.add(Calendar.DAY_OF_MONTH,3);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        endDT = new DateTime().withDate(year,month+1,day).withTimeAtStartOfDay();
        endDate = year + "/"+(month+1)+"/"+day+" "+hour+":"+min;;
        try{
            endMilliTime = formater.parse(endDate).getTime();
            Log.e("endMilliTime",endMilliTime+"");
        }catch (ParseException e){
            Log.e("ParseException",e.getMessage());
        }
        //projectEndTime.setText(endDT.toString(DateTimeFormat.forPattern("yyyy/MM/dd HH:mm")));
        projectEndTime.setText(formater.format(calendar.getTime()));
    }

    private void setBar() {
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("第1个计划");
            // actionBar.setSubtitle("新建计划");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home){
            //navigateUpTo(new Intent(this,MainActivity.class));
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.plan_complete:
                try {
                    plan.setName(contentEditView.getText().toString());
                    plan.setGoal(goalEditView.getText().toString());
                    plan.setStatus(projectStatusSp.getSelectedItemPosition());
                    plan.setPlanType(planPriority.getSelectedItemPosition());
                    plan.setStartDate(startMilliTime);
                    plan.setEndDate(endMilliTime);
                    plan.setPlanType(planPriority.getSelectedItemPosition());
                    if (!isEdit){
                        plan.setPlanType(0);
                    }

                    if (projectMemo.getText().toString().length() > 0){
                        plan.setMemo(projectMemo.getText().toString());
                    }
                    if (planDao.save(plan) < 0) {
                        throw new Exception("could not save Task");
                    }
                    toast("保存成功");
                    finish();
                } catch (Exception e) {
                    Log.e("SaveExp", e.getMessage() + "<>" + e.getCause());
                    e.printStackTrace();
                    toast("保存失败");
                }
                break;
            case R.id.plan_delete:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setTitle(contentEditView.getText().toString());
                alertBuilder.setMessage(R.string.delete_warn);
                alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            if (planDao.deleteByProjectID(plan.getProjectID()) < 0){
                                throw new Exception("不能删除任务");
                            }
                            toast("删除成功!");
                            finish();
                        }catch (Exception e){
                            e.printStackTrace();
                            toast("删除失败!");
                        }
                    }
                });
                alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertBuilder.setCancelable(true);
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();
                break;
            case R.id.project_starttime:

                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        startDate = year + "/"+(monthOfYear+1)+"/"+dayOfMonth;
                        try{
                            projectStartTime.setText(formater.format(formater.parse(startDate)));
                            startMilliTime = formater.parse(startDate).getTime();
                        }catch (ParseException e){

                        }

                        new TimePickerDialog(NewPlanActivity.this, new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int min) {
                                startDate = startDate+" "+ hour+":"+min;
                                try{
                                    projectStartTime.setText(formater.format(formater.parse(startDate)));
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
            case R.id.project_endtime:
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        endDate = year + "/"+(monthOfYear+1)+"/"+dayOfMonth;
                        try{
                            projectEndTime.setText(formater.format(formater.parse(endDate)));
                            endMilliTime = formater.parse(endDate).getTime();
                        }catch (ParseException e){

                        }

                        new TimePickerDialog(NewPlanActivity.this, new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int min) {
                                endDate = endDate+" "+ hour+":"+min;
                                try{
                                    projectEndTime.setText(formater.format(formater.parse(endDate)));
                                    endMilliTime = formater.parse(endDate).getTime();
                                }catch (ParseException e){

                                }
                            }
                        },hour,min,true).show();

                        //endDT = new DateTime().withDate(year,monthOfYear+1,dayOfMonth).withTimeAtStartOfDay();
                        //projectEndTime.setText(endDT.toString(DateTimeFormat.forPattern("yyyy/MM/dd HH:mm")));
                    }
                },year,month,day).show();
                break;
        }
    }
    public long getMilliStartDate(){
        return startDT.getMillis();
    }
    public long getMilliEndDate(){
        return endDT.getMillis();
    }
    private  void getDate(){
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH)  ;
        day = c.get(Calendar.DAY_OF_MONTH)  ;
        hour=c.get(Calendar.HOUR_OF_DAY);
        min=c.get(Calendar.MINUTE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
