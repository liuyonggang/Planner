package com.lyg.planner.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lyg.planner.dao.BaseDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/3/9.
 */
public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public Typeface iconFont,xiyuanFont,newIcFont;
    public LayoutInflater inflater = null;
    Context context;
    ArrayList<HashMap<String, String>> data;

    public Adapter(Context context){
        this.context = context;
        init();
    }
    public Adapter(Context context,List<?> data,BaseDao daoObj){
        this.context = context;
        init();
    }
    public Adapter(Context context, ArrayList<HashMap<String, String>> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        init();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void init(){
        inflater    = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(iconFont == null){
            iconFont=Typeface.createFromAsset(context.getAssets(), "iconfont.ttf") ;
        }
        if(xiyuanFont==null){
            xiyuanFont=Typeface.createFromAsset(context.getAssets(), "fzxiyuan.TTF") ;
        }
        if(newIcFont == null){
            newIcFont = Typeface.createFromAsset(context.getAssets(), "newiconfont.ttf") ;
        }
    }

}
