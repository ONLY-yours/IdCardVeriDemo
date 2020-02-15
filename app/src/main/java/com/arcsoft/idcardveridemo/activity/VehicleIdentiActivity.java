package com.arcsoft.idcardveridemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.arcsoft.idcardveridemo.R;
import com.arcsoft.idcardveridemo.base.BaseActivity;

public class VehicleIdentiActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivFaceFindVehicle;
    private ImageView ivLicenseFindVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_vehicle_identi);
        initView();
    }
    void initView(){
        ivFaceFindVehicle=findViewById(R.id.iv_faceFindVehicle);
        ivLicenseFindVehicle=findViewById(R.id.iv_licenseFindVehicle);
        ivFaceFindVehicle.setOnClickListener(this);
        ivLicenseFindVehicle.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_faceFindVehicle:
//                startActivity();
            case R.id.iv_licenseFindVehicle:
                startActivity(LicenseSearchActivity.class);
        }

    }
}
