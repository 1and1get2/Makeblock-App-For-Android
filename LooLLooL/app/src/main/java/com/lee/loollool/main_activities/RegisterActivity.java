package com.lee.loollool.main_activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import activities.loollool.lee.com.loollool.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView rg_denglu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        rg_denglu = (ImageView) findViewById(R.id.rg_denlu);
        rg_denglu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rg_denlu:
                finish();
                break;

        }
    }
}
