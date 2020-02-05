package com.arcsoft.idcardveridemo.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.arcsoft.idcardveri.CompareResult;
import com.arcsoft.idcardveri.DetectFaceResult;
import com.arcsoft.idcardveri.IdCardVerifyError;
import com.arcsoft.idcardveri.IdCardVerifyListener;
import com.arcsoft.idcardveri.IdCardVerifyManager;
import com.arcsoft.idcardveridemo.DrawUtils;
import com.arcsoft.idcardveridemo.ImageChange.ChangeNv21;
import com.arcsoft.idcardveridemo.MainActivity;
import com.arcsoft.idcardveridemo.base.BaseActivity;
import com.arcsoft.idcardveridemo.R;
import com.arcsoft.idcardveridemo.constants.Constants;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WelcomeActivity extends BaseActivity implements SurfaceHolder.Callback{

    private SurfaceView surfaceView;
    private SurfaceView surfaceRect;
    private Button btnUse;
    private Button btnSelectPhoto;

    private int camereId = 0;
    private Camera camera;
    private long startTime = 0;

    private boolean isInit = false; //用于判断引擎是否激活
    //视频或图片人脸数据是否检测到
    private boolean isCurrentReady = false;
    //身份证人脸数据是否检测到
    private boolean isIdCardReady = false;

    //打开相册的标记
    private static final int REQUEST_CODE_SCAN_GALLERY = 1 ;
    private Bitmap IDimg=null;

    private ExecutorService activeService = Executors.newSingleThreadExecutor();

    private IdCardVerifyListener idCardVerifyListener = new IdCardVerifyListener() {                //
        @Override
        public void onPreviewResult(DetectFaceResult detectFaceResult, byte[] bytes, int i, int i1) {
            if(detectFaceResult.getErrCode() == IdCardVerifyError.OK) {
                isCurrentReady = true;
                //需要在主线程进行比对
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        compare();
                    }
                });
            }
        }

        @Override
        public void onIdCardResult(DetectFaceResult detectFaceResult, byte[] bytes, int i, int i1) {
            if(detectFaceResult.getErrCode() == IdCardVerifyError.OK) {
                isIdCardReady = true;
                //需要在主线程进行比对
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        compare();
                    }
                });
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_welcome);
        //授权
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
            return;
        }
        initEngine();
        initView();
        btnUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //输入身份证数据
            startTime = System.currentTimeMillis();
               inputIdCard();
            }
        });
//        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openAlbum();
//            }
//        });
    }

    //用于激活引擎（初始化）
    void initEngine(){
        //初始化
        int initResult = IdCardVerifyManager.getInstance().init(this, idCardVerifyListener);
        isInit = initResult == IdCardVerifyError.OK;
        Log.i(TAG, "init result: " + initResult);
        if(!isInit) {
            Toast.makeText(WelcomeActivity.this, "init result: "
                    + initResult, Toast.LENGTH_LONG).show();
        }

        activeService.execute(new Runnable() {
            @Override
            public void run() {
                final int activeResult = IdCardVerifyManager.getInstance().active(
                        WelcomeActivity.this, Constants.APP_ID, Constants.SDK_KEY);
                Log.i(TAG, "active result: " + activeResult);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(activeResult==90114){
                           showToast("引擎已经激活,可以正常使用");
                        }else{
                            showToast("引擎激活失败，请获取激活码或是稍后再试");
                        }

//                        Toast.makeText(WelcomeActivity.this, "active result: "
//                                + activeResult, Toast.LENGTH_LONG).show();
                        if(activeResult == IdCardVerifyError.OK) {
                            //初始化
                            int initResult = IdCardVerifyManager.getInstance().init(
                                    WelcomeActivity.this, idCardVerifyListener);
                            isInit = initResult == IdCardVerifyError.OK;
                            Log.i(TAG, "init result: " + initResult);
                            if(!isInit) {
//                                Toast.makeText(WelcomeActivity.this, "init result: "
//                                        + initResult, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });

    }

    void initView(){
        surfaceView = findViewById(R.id.surfaceView);
        SurfaceHolder surfaceholder = surfaceView.getHolder();
        surfaceholder.addCallback(this);
        surfaceRect = findViewById(R.id.surfaceview_rect);
        surfaceRect.setZOrderMediaOverlay(true);
        surfaceRect.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        btnUse=findViewById(R.id.btn_idcard);
        btnSelectPhoto=findViewById(R.id.btn_photoselect);
    }



    private void inputIdCard() {
        //身份证数据 根据实际数据输入
        byte[] nv21Data = new byte[1024];
        //身份证数据宽（宽度需为4的倍数）
        int width = 0;
        //身份证数据高（高度需为2的倍数）
        int height = 0;

        Drawable drawable = getResources().getDrawable(R.mipmap.idimg);
        BitmapDrawable bd = (BitmapDrawable) drawable;
        final Bitmap bmm = bd.getBitmap();
        width=(bmm.getWidth()-bmm.getWidth()%4);
        height=(bmm.getHeight()-bmm.getHeight()%2);
        nv21Data = ChangeNv21.bitmapToNv21(bmm,width,height);

        if(IDimg!=null){
            width=(IDimg.getWidth()-IDimg.getWidth()%4);
            height=(IDimg.getHeight()-IDimg.getHeight()%2);
            nv21Data = ChangeNv21.bitmapToNv21(IDimg,width,height);
        }

        if(isInit) {
            DetectFaceResult result = IdCardVerifyManager.getInstance().inputIdCardData(nv21Data, width, height);
            Log.i(TAG, "inputIdCardData result: " + result.getErrCode());
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camereId = Camera.getNumberOfCameras() - 1;
        camera = Camera.open(camereId);
        try {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size previewSize = getBestSupportedSize(parameters.getSupportedPreviewSizes(), metrics);
            parameters.setPreviewSize(previewSize.width, previewSize.height);
            camera.setParameters(parameters);
            final int mWidth = previewSize.width;
            final int mHeight = previewSize.height;
            displayOrientation = getCameraOri(getWindowManager().getDefaultDisplay().getRotation());
            camera.setDisplayOrientation(displayOrientation);
            camera.setPreviewDisplay(holder);
            camera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    //视频数据
                    if(isInit) {
                        DetectFaceResult result = IdCardVerifyManager.getInstance().onPreviewData(data, mWidth, mHeight, true);
                        if (result.getErrCode() != IdCardVerifyError.OK) {
                            Log.i(TAG, "onPreviewData video result: " + result.getErrCode());
                        }else{
                            //异步处理脸部
                        }
                        if (surfaceRect != null) {
                            Canvas canvas = surfaceRect.getHolder().lockCanvas();
                            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                            Rect rect = result.getFaceRect();
                            if (rect != null) {
                                Rect adjustedRect = DrawUtils.adjustRect(rect, mWidth, mHeight,
                                        canvas.getWidth(), canvas.getHeight(), displayOrientation, camereId);
                                //画人脸框
                                DrawUtils.drawFaceRect(canvas, adjustedRect, Color.YELLOW, 5);
                            }
                            surfaceRect.getHolder().unlockCanvasAndPost(canvas);
                        }
                    }
                }
            });
            camera.startPreview();
        } catch (Exception e) {
            camera = null;
        }
    }



    private void compare() {
        if(!isCurrentReady || !isIdCardReady) {
            return;
        }
        //人证比对
        CompareResult compareResult = IdCardVerifyManager.getInstance().compareFeature(THRESHOLD);
        Log.i(TAG, "compareFeature: result " + compareResult.getResult() + ", isSuccess "
                + compareResult.isSuccess() + ", errCode " + compareResult.getErrCode());
//        Toast.makeText(this, "compareFeature: result " + compareResult.getResult()
//                + ", costTime " + (System.currentTimeMillis() - startTime) + ", isSuccess "
//                + compareResult.isSuccess() + ", errCode " + compareResult.getErrCode(), Toast.LENGTH_LONG).show();
        if( isIdCardReady==true && isCurrentReady==true && compareResult.isSuccess()==true ){
            showToast("欢迎您,尊敬的xxx先生");
        }
        isIdCardReady = false;
        isCurrentReady = false;
    }





    @Override
    protected void onDestroy() {
//        反初始化
        if(activeService != null) {
            activeService.shutdown();
        }
        IdCardVerifyManager.getInstance().unInit();
        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
        super.onDestroy();
    }


    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes, DisplayMetrics metrics) {
        Camera.Size bestSize = sizes.get(0);
        float screenRatio = (float) metrics.widthPixels / (float) metrics.heightPixels;
        if (screenRatio > 1) {
            screenRatio = 1 / screenRatio;
        }

        for (Camera.Size s : sizes) {
            if (Math.abs((s.height / (float) s.width) - screenRatio) < Math.abs(bestSize.height /
                    (float) bestSize.width - screenRatio)) {
                bestSize = s;
            }
        }
        return bestSize;
    }

    private int getCameraOri(int rotation) {
        int degrees = rotation * 90;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
            default:
                break;
        }
        int result;
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(camereId, info);
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }

    //打开相册选择
    private void openAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CODE_SCAN_GALLERY);//打开系统相册
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode==RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_SCAN_GALLERY:
                    Toast.makeText(this,""+data.getDataString(),Toast.LENGTH_LONG).show();
                    Bitmap bitmap= null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),data.getData());
                        IDimg = MediaStore.Images.Media.getBitmap(this.getContentResolver(),data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}
