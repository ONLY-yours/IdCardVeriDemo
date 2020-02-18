package com.arcsoft.idcardveridemo.activity.checkIn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.arcsoft.idcardveridemo.R;
import com.arcsoft.idcardveridemo.base.BaseActivity;

public class ArcConfireActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivCheckArc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_arc_confire);

        ivCheckArc = findViewById(R.id.iv_checkArc);
        ivCheckArc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_checkArc:
                startActivity(FinaCheckActivity.class);
                break;
        }
    }
}
