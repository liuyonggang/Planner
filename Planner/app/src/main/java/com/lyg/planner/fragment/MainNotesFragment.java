package com.lyg.planner.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyg.planner.BaseActivity;
import com.lyg.planner.R;

/**
 * Created by Administrator on 2016/4/28.
 */
public class MainNotesFragment extends BaseFragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main_notes,container,false);
        return view;
    }
}
