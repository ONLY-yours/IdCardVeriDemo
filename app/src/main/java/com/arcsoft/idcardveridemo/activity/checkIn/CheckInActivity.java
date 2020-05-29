package com.arcsoft.idcardveridemo.activity.checkIn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.arcsoft.idcardveridemo.R;
import com.arcsoft.idcardveridemo.adapter.OrderListAdapter;
import com.arcsoft.idcardveridemo.base.BaseActivity;

import java.io.IOException;

public class CheckInActivity extends BaseActivity implements View.OnClickListener {

    private View btnBack;
    private View viewPlaceId;

    public static Bitmap Idimg;

    private static final int REQUEST_CODE_SCAN_GALLERY = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_check_in);
        initView();
    }


    void initView(){
        btnBack=findViewById(R.id.btnBack);
        viewPlaceId=findViewById(R.id.view_cardplace);

        btnBack.setOnClickListener(this);
        viewPlaceId.setOnClickListener(this);
    }

    private void openAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CODE_SCAN_GALLERY);//打开系统相册
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:{
                onBackPressed();
             break;
            }
            case R.id.view_cardplace:
                if (!checkPermissions(NEEDED_PERMISSIONS)) {
                    ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
                    return;
                } else {
                    //打开系统相册
                    openAlbum();
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode==RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_SCAN_GALLERY:
//                    Toast.makeText(this,""+data.getDataString(),Toast.LENGTH_LONG).show();
                    Bitmap bitmap= null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),data.getData());
                        Idimg = MediaStore.Images.Media.getBitmap(this.getContentResolver(),data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    ivPhoto.setImageBitmap(bitmap);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
//        startActivity(ConfireOrderActivity.class);
            startActivity(OrderListActivity.class);
    }

}
