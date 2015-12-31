package com.lee.loollool.car_activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import activities.loollool.lee.com.loollool.R;

public class TimerTaskActivity extends Activity {

    private int reclen = 5;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_timer_task);

        imageView = (ImageView) findViewById(R.id.img_time);
        handler.postDelayed(runnable, 1000);

    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            reclen--;
            switch (reclen) {
                case 4:
                    imageView.setImageResource(R.drawable.time_count_4);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.time_count_3);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.time_count_2);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.time_count_1);
                    break;
                case 0:
                    imageView.setImageResource(R.drawable.time_count_0);
                    break;
            }
            if (reclen < 0) {
                finish();
            }
            handler.postDelayed(this, 1000);
        }
    };
}
