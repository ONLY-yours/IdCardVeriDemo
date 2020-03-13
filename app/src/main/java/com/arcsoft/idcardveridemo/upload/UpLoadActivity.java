package com.arcsoft.idcardveridemo.upload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arcsoft.idcardveridemo.R;
import com.arcsoft.idcardveridemo.base.BaseActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UpLoadActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv;
    private Button btnget;

    //服务器地址
    public static final String  HTTP_Url="http://www.kuaidi100.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_up_load);

        tv=findViewById(R.id.tv);
        btnget=findViewById(R.id.get);

        btnget.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get: {

                };
            }
        }
    }

