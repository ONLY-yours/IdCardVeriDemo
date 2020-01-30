package com.arcsoft.idcardveridemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.arcsoft.idcardveridemo.base.BaseActivity;
import com.arcsoft.idcardveridemo.MainActivity;
import com.arcsoft.idcardveridemo.R;

public class LoadingActivity extends BaseActivity implements View.OnClickListener {

    private Button btnStart;
    private Button btnWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_loading);
        initView();
    }

    void initView(){
        btnWelcome=findViewById(R.id.btnWelcome);
        btnStart=findViewById(R.id.btnStart);
        btnWelcome.setOnClickListener(this);
        btnStart.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStart:
                startActivity(MainActivity.class);
                break;
            case R.id.btnWelcome:
                startActivity(WelcomeActivity.class);
                break;
        }
    }
}
