package com.lee.loollool.car_activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import activities.loollool.lee.com.loollool.R;

/**
 * Created by derek on 31/12/15.
 */
public class TimerView extends RelativeLayout{
    private static final String TAG = "TimerView";
    private ImageView imageView;
    private final @IdRes int[] imagesId = {R.drawable.time_count_5, R.drawable.time_count_4, R.drawable.time_count_3, R.drawable.time_count_2, R.drawable.time_count_1, R.drawable.time_count_0};
    private int currentIndex = 0;
    private Handler handler = new Handler();

    public TimerView(Context context) {
        super(context);
        init(context);
    }

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context){
        imageView = new ImageView(context);
        addView(imageView, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.CENTER);
        //imageView.setImageResource(R.drawable.time_count_4);
        setAlpha(0.5f);
        setBackgroundColor(Color.BLACK);
        //setVisibility(GONE);
        stop();
    }
    public void start(){
        currentIndex = 0;
        setVisibility(VISIBLE);
        handler.postDelayed(runner, 0);
    }
    public void stop(){
        handler.removeCallbacks(runner);
        currentIndex = 0;
        setVisibility(GONE);
    }
    public void toggle(){
        if (getVisibility() == VISIBLE) {
            stop();
        } else {
            start();
        }
    }
    private final Runnable runner = new Runnable() {
        @Override
        public void run() {
            if (currentIndex < 6) {
                imageView.setImageResource(imagesId[currentIndex]);
                imageView.invalidate();
                currentIndex ++;
                handler.postDelayed(runner, 1000);
            } else {
                stop();
            }
        }
    };

}
