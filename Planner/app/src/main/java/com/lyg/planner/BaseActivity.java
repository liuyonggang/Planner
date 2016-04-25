package com.lyg.planner;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/3/3.
 */
public class BaseActivity extends AppCompatActivity{

    public Typeface iconFont,xiyuanFont;
    public DateFormat formater = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /*页面跳转*/
    public void forward(Class<?> classObj){
        Intent intent = new Intent();
        intent.setClass(this,classObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }
    public void forward(Class<?> classObj,Bundle data){
        Intent intent = new Intent(this,classObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(data);
        this.startActivity(intent);
    }
    /**
     * 提示
     */
    public void toast (String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public Typeface getIconFont(){
        if(iconFont == null){
            iconFont=Typeface.createFromAsset(getAssets(), "iconfont.ttf") ;
        }
        return iconFont;
    }
    public Typeface getFZXiYuanFont(){
        if(xiyuanFont==null){
            xiyuanFont=Typeface.createFromAsset(getAssets(), "fzxiyuan.TTF") ;
        }
        return xiyuanFont;
    }

}
