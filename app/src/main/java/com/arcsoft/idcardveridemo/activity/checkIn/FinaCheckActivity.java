package com.arcsoft.idcardveridemo.activity.checkIn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.arcsoft.idcardveridemo.R;
import com.arcsoft.idcardveridemo.activity.WelcomeActivity;
import com.arcsoft.idcardveridemo.base.BaseActivity;

public class FinaCheckActivity extends BaseActivity implements View.OnClickListener {

    private View viewBack;

    private ImageView ivdemo;

    private View btnCheckIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_fina_check);

//        ivdemo=findViewById(R.id.iv_demo);
//        ivdemo.setImageBitmap(WelcomeActivity.preIMG);

        viewBack=findViewById(R.id.btnBack);
        viewBack.setOnClickListener(this);

        btnCheckIn=findViewById(R.id.view_finalcheckin);
        btnCheckIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.view_finalcheckin:
                showToast("入住成功，请享受愉快的入住体验！");
                startActivity(LoadViewActivity.class);
                break;
        }
    }
}
