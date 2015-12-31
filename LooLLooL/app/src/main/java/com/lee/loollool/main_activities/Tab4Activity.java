package com.lee.loollool.main_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import activities.loollool.lee.com.loollool.R;

public class Tab4Activity extends AppCompatActivity {
    Button button1;
    TextView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4);
        button = (TextView) findViewById(R.id.tab_4_denglu);
        button1 = (Button) findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tab4Activity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tab4Activity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
    }
}
