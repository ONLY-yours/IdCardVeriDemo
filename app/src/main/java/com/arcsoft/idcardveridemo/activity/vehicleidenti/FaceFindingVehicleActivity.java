package com.arcsoft.idcardveridemo.activity.vehicleidenti;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.arcsoft.idcardveridemo.MainActivity;
import com.arcsoft.idcardveridemo.R;
import com.arcsoft.idcardveridemo.activity.checkIn.ConfireOrderActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FaceFindingVehicleActivity extends AppCompatActivity {

    public static final int TAKE_PHOTO = 1;
    private ImageView pic;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_finding_vehicle);
        initView();
        //创建File对象，用于存储拍摄后的图片
        File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
        try {
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //得到Uri
        if(Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(FaceFindingVehicleActivity.this,
                    "com.arcsoft.idcardveridemo.fileprovider",outputImage);
        }else {
            imageUri = Uri.fromFile(outputImage);
        }
//        //启动相机程序
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
//        startActivityForResult(intent, TAKE_PHOTO);





    }
    void initView(){
        pic = findViewById(R.id.iv_face_finding_vehicle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    try{
                        //讲拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        pic.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}
