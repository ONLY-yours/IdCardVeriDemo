package com.arcsoft.idcardveridemo.activity.checkIn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.arcsoft.idcardveridemo.R;
import com.arcsoft.idcardveridemo.base.BaseActivity;

public class ConfireOrderActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_confire_order);

        ivTest=findViewById(R.id.iv_test);
        ivTest.setImageBitmap(CheckInActivity.Idimg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){


        }
    }
}
