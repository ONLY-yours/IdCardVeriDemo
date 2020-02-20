package com.arcsoft.idcardveridemo.activity.checkIn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.arcsoft.idcardveridemo.R;
import com.arcsoft.idcardveridemo.base.BaseActivity;

public class FinaCheckActivity extends BaseActivity implements View.OnClickListener {

    private View viewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_fina_check);

        viewBack=findViewById(R.id.btnBack);
        viewBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }
}
