package com.arcsoft.idcardveridemo.activity.checkIn;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.arcsoft.idcardveri.CompareResult;
import com.arcsoft.idcardveri.DetectFaceResult;
import com.arcsoft.idcardveri.IdCardVerifyError;
import com.arcsoft.idcardveri.IdCardVerifyListener;
import com.arcsoft.idcardveri.IdCardVerifyManager;
import com.arcsoft.idcardveridemo.DrawUtils;
import com.arcsoft.idcardveridemo.ImageChange.ChangeNv21;
import com.arcsoft.idcardveridemo.R;
import com.arcsoft.idcardveridemo.activity.WelcomeActivity;
import com.arcsoft.idcardveridemo.base.BaseActivity;
import com.arcsoft.idcardveridemo.constants.Constants;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.arcsoft.idcardveridemo.activity.checkIn.CheckInActivity.Idimg;

public class ArcConfireActivity extends BaseActivity implements View.OnClickListener , SurfaceHolder.Callback{

    private ImageView ivCheckArc;


    private SurfaceView surfaceView;
    private SurfaceView surfaceRect;
    private Button test;

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
        setContentView(R.layout.act_arc_confire);

        //授权
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
            return;
        }
        initView();
        initEngine();
        inputIdCard();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_checkArc:
                finish();
                startActivity(FinaCheckActivity.class);
                break;
            case R.id.test:
                //输入身份证数据
                startTime = System.currentTimeMillis();
                inputIdCard();
                break;
        }
    }

    void initView(){
        surfaceView = findViewById(R.id.surfaceView);
        SurfaceHolder surfaceholder = surfaceView.getHolder();
        surfaceholder.addCallback(this);
        surfaceRect = findViewById(R.id.surfaceview_rect);
        surfaceRect.setZOrderMediaOverlay(true);
        surfaceRect.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        ivCheckArc = findViewById(R.id.iv_checkArc);
        ivCheckArc.setOnClickListener(this);

        test=findViewById(R.id.test);
        test.setOnClickListener(this);
    }


    //用于激活引擎（初始化）
    void initEngine(){
        //初始化
        int initResult = IdCardVerifyManager.getInstance().init(this, idCardVerifyListener);
        isInit = initResult == IdCardVerifyError.OK;
        Log.i(TAG, "init result: " + initResult);
        if(!isInit) {
            Toast.makeText(ArcConfireActivity.this, "init result: "
                    + initResult, Toast.LENGTH_LONG).show();
        }

        activeService.execute(new Runnable() {
            @Override
            public void run() {
                final int activeResult = IdCardVerifyManager.getInstance().active(
                        ArcConfireActivity.this, Constants.APP_ID, Constants.SDK_KEY);
                Log.i(TAG, "active result: " + activeResult);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(activeResult==90114){
//                            showToast("引擎已经激活,可以正常使用");
                        }else{
                            showToast("引擎激活失败，请获取激活码或是稍后再试");
                        }
                        if(activeResult == IdCardVerifyError.OK) {
                            //初始化
                            int initResult = IdCardVerifyManager.getInstance().init(
                                    ArcConfireActivity.this, idCardVerifyListener);
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

//        if(Idimg!=null){
//            width=(Idimg.getWidth()-Idimg.getWidth()%4);
//            height=(Idimg.getHeight()-Idimg.getHeight()%2);
//            nv21Data = ChangeNv21.bitmapToNv21(Idimg,width,height);
//        }

        if(isInit) {
            DetectFaceResult result = IdCardVerifyManager.getInstance().inputIdCardData(nv21Data, width, height);
            Log.i(TAG, "inputIdCardData result: " + result.getErrCode());
        }
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

                        inputIdCard();

                        if (surfaceRect != null) {
                            Canvas canvas = surfaceRect.getHolder().lockCanvas();
                            canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
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



    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
        camera = null;
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
    public void onBackPressed() {
        finish();

//        startActivity(ConfireOrderActivity.class);
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

}
