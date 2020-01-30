package com.arcsoft.idcardveridemo.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.arcsoft.idcardveridemo.utils.ToastUtil;

import java.io.Serializable;

/**
 * @auther: lijunjie
 * @createDate: 2020/1/29  18:24
 * @purpose：
 */
public abstract   class BaseActivity extends AppCompatActivity {

    public static final String TAG = "IDcard service up";
    //比对阈值，建议为0.82
    public static final double THRESHOLD = 0.82d;
    public static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    // 在线激活所需的权限
    public static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    public int displayOrientation = 0;

    //用于快速授权
    protected boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    //用于快速启动活动
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        //        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * 弹出界面提示内容
     *
     * @param msg 提示内容文字内容
     */
    public void showToast(String msg) {
        ToastUtil.toastShortShow(getApplication(), msg);
    }



}
