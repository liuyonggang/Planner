package com.lyg.planner;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SplashActivity extends Activity {

    RelativeLayout rootLayout;
    SplashTimer splashTimer;
    TextView titmeTip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rootLayout = (RelativeLayout)findViewById(R.id.root_splash);
        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMain();
                splashTimer.cancel();
            }
        });

        titmeTip = (TextView)findViewById(R.id.splash_time_tip);

        splashTimer = new SplashTimer(3000,1000);
        splashTimer.start();

    }

    /*页面跳转*/
    public void forward(Class<?> classObj){
        Intent intent = new Intent();
        intent.setClass(this,classObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }

    private void toMain() {
        forward(MainActivity.class);
        SplashActivity.this.finish();
    }

    class SplashTimer extends CountDownTimer{

        public SplashTimer(long millisInFuture,long countDownInterval){
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            titmeTip.setText(millisUntilFinished / 1000 + "秒 跳过>");
        }

        @Override
        public void onFinish() {
            toMain();
        }
    }


}
