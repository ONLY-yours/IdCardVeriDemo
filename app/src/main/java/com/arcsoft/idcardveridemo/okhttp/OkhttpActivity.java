package com.arcsoft.idcardveridemo.okhttp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpActivity extends AppCompatActivity {
    /**
     *
     * @param url
     * @param imagePath

     */

   public static void uploadImage(String url, String imagePath, final Context context){

       OkHttpClient okHttpClient = new OkHttpClient();
       Log.d("imagePath",imagePath);

       //获取要上传的文件
       File file = new File(imagePath);
       //设置文件以及文件上传类型封装
       RequestBody image = RequestBody.create(MediaType.parse("image/png"),file);
       RequestBody requestBody = new MultipartBody.Builder()
               .setType(MultipartBody.FORM)
               .addFormDataPart("file",imagePath,image)
               .build();

       Request request = new Request.Builder()
               .url(url)
               .post(requestBody)
               .build();
       Call call = okHttpClient.newCall(request);
       call.enqueue(new Callback() {
           @Override
           public void onFailure(@NotNull Call call, @NotNull IOException e) {
               Toast.makeText(context,"上传失败",Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
               Toast.makeText(context,"上传成功",Toast.LENGTH_SHORT).show();

           }
       });



    }

}
