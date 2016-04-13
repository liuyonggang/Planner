package com.lyg.planner.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.lyg.planner.R;
import com.lyg.planner.adapter.PlanAdapter;
import com.lyg.planner.dao.PlanDao;
import com.lyg.planner.model.Plan;
import com.lyg.planner.util.DatabaseHelper;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4.
 */
public class MainPlanFragment extends BaseFragment{
    RecyclerView planList;
    PlanAdapter planAdapter;
    PlanDao planDao;

    List<Plan> plans;
    View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_main_plan,container,false);

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

        init();
       // setListAnim();
    }
    private void init() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        planDao = new PlanDao(db);

        plans = planDao.findAll();
        plans.add(new Plan());

        planList = (RecyclerView)view.findViewById(R.id.plan_list);
        planList.setLayoutManager(new LinearLayoutManager(getContext()));
        planAdapter = new PlanAdapter(getContext(),plans,planDao);
        planList.setAdapter(planAdapter);
    }
    private void setListAnim(){
        /**
         * 为ListView 添加动画
         * */
        Animation animation = (Animation) AnimationUtils.loadAnimation(
                getContext(), R.anim.list_anim_y);
        LayoutAnimationController lac = new LayoutAnimationController(animation);
        lac.setDelay(0.5f);  //设置动画间隔时间
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL); //设置列表的显示顺序
        planList.setLayoutAnimation(lac);
    }
    public void onPause(){
        super.onPause();
    }
}
