package com.lyg.planner.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2016/3/4.
 */
public class BaseFragment extends Fragment {


    public Context getFraContext() {
        return getActivity();
    }
}
