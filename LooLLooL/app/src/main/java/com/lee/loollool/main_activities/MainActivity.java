package com.lee.loollool.main_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import activities.loollool.lee.com.loollool.R;

public class MainActivity extends android.app.TabActivity implements View.OnClickListener {

    private TabHost tabHost;
    private RelativeLayout rl_01;
    private RelativeLayout rl_02;
    private RelativeLayout rl_03;
    private RelativeLayout rl_04;

    private ImageView img_01;
    private ImageView img_02;
    private ImageView img_03;
    private ImageView img_04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
    }

    public void initLayout() {

        tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Tab1").setContent(new Intent(this, Tab1Activity.class)));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Tab2").setContent(new Intent(this, Tab2Activity.class)));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Tab3").setContent(new Intent(this, Tab3Activity.class)));
        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("Tab4").setContent(new Intent(this, Tab4Activity.class)));

        rl_01 = (RelativeLayout) findViewById(R.id.rl_01);
        rl_02 = (RelativeLayout) findViewById(R.id.rl_02);
        rl_03 = (RelativeLayout) findViewById(R.id.rl_03);
        rl_04 = (RelativeLayout) findViewById(R.id.rl_04);

        img_01 = (ImageView) findViewById(R.id.img_01);
        img_02 = (ImageView) findViewById(R.id.img_02);
        img_03 = (ImageView) findViewById(R.id.img_03);
        img_04 = (ImageView) findViewById(R.id.img_04);

        rl_01.setOnClickListener(this);
        rl_02.setOnClickListener(this);
        rl_03.setOnClickListener(this);
        rl_04.setOnClickListener(this);

        img_01.setBackgroundResource(R.mipmap.jiqiren);
        img_02.setBackgroundResource(R.mipmap.chuangyiqianghui);
        img_03.setBackgroundResource(R.mipmap.yunketanghui);
        img_04.setBackgroundResource(R.mipmap.wohui);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_01:
                tabHost.setCurrentTabByTag("tab1");
                img_01.setBackgroundResource(R.mipmap.jiqiren);
                img_02.setBackgroundResource(R.mipmap.chuangyiqianghui);
                img_03.setBackgroundResource(R.mipmap.yunketanghui);
                img_04.setBackgroundResource(R.mipmap.wohui);
                break;
            case R.id.rl_02:
                tabHost.setCurrentTabByTag("tab2");
                img_01.setBackgroundResource(R.mipmap.jiqirenhui);
                img_02.setBackgroundResource(R.mipmap.chuangyiqiang);
                img_03.setBackgroundResource(R.mipmap.yunketanghui);
                img_04.setBackgroundResource(R.mipmap.wohui);
                break;
            case R.id.rl_03:
                tabHost.setCurrentTabByTag("tab3");
                img_01.setBackgroundResource(R.mipmap.jiqirenhui);
                img_02.setBackgroundResource(R.mipmap.chuangyiqianghui);
                img_03.setBackgroundResource(R.mipmap.yunketang);
                img_04.setBackgroundResource(R.mipmap.wohui);
                break;
            case R.id.rl_04:
                tabHost.setCurrentTabByTag("tab4");
                img_01.setBackgroundResource(R.mipmap.jiqirenhui);
                img_02.setBackgroundResource(R.mipmap.chuangyiqianghui);
                img_03.setBackgroundResource(R.mipmap.yunketanghui);
                img_04.setBackgroundResource(R.mipmap.wo);
                break;
        }
    }
}
