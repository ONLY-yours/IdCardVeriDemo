package com.arcsoft.idcardveridemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.arcsoft.idcardveridemo.R;
import com.arcsoft.idcardveridemo.base.BaseActivity;

public class CheckInActivity extends BaseActivity implements View.OnClickListener {

    private View btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_check_in);
        initView();

    }


    void initView(){
        btnBack=findViewById(R.id.btnBack);

        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:{
                onBackPressed();
             break;
            }
            default:
                break;
        }
    }
}
