package com.lee.loollool.Modles;

import com.lee.loollool.bean.MeModule;

import org.json.JSONObject;

import activities.loollool.lee.com.loollool.R;


/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class MeFengMingQi extends MeModule {

    static String devName = "fengmingqi";

    public MeFengMingQi(int port, int slot) {
        super(devName, MeModule.DEV_FENGMINGQI, port, slot);
        viewLayout = R.layout.dev_switch_view;
        imageId = R.mipmap.fengmingqi_other;
    }

    public MeFengMingQi(JSONObject json) {
        super(json);
        viewLayout = R.layout.dev_switch_view;
        imageId = R.mipmap.fengmingqi_other;
    }
}
