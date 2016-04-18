package com.lyg.planner.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyg.planner.BaseActivity;
import com.lyg.planner.R;
import com.lyg.planner.adapter.SubPlanAdapter;

/**
 * Created by Administrator on 2016/4/13.
 */
public class SubPlanActivity extends BaseActivity{

    private LinearLayout addPlan;
    private RecyclerView subPlanList;
    private SubPlanAdapter subPlanAdapter;
    private FloatingActionButton addPlanFB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subplan);
        setBar();
        /**
         * break down plan
         */
        initSubPlan();
    }
    private void initSubPlan() {
        subPlanList = (RecyclerView)findViewById(R.id.sub_plan_list);
        subPlanList.setLayoutManager(new LinearLayoutManager(this));
        subPlanAdapter = new SubPlanAdapter(this);
        subPlanList.setAdapter(subPlanAdapter);

        addPlan = (LinearLayout)findViewById(R.id.add_sub_plan);
        addPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddPlanPopupWindow(v);
            }


        });

        addPlanFB = (FloatingActionButton)findViewById(R.id.subplan_add_fb);
        addPlanFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddPlanPopupWindow(v);
            }
        });
    }
    private void showAddPlanPopupWindow(View v) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.addplan_pop_window, null);


        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setAnimationStyle(R.style.windowAnim);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.ripple_bg));

        // 设置好参数之后再show
        popupWindow.showAtLocation(findViewById(R.id.root_subplan), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);

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
}
