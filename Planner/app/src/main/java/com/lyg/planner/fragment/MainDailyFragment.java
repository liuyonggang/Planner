package com.lyg.planner.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyg.planner.R;

/**
 * Created by Administrator on 2016/3/4.
 */
public class MainDailyFragment extends BaseFragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main_daily,container,false);
        return view;
    }
}
