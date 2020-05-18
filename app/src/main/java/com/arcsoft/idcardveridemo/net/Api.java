package com.arcsoft.idcardveridemo.net;

import com.arcsoft.idcardveridemo.bean.ImgUpBean;
import com.arcsoft.idcardveridemo.bean.LoginBean;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * @auther: lijunjie
 * @createDate: 2020/3/12  19:32
 * @purpose：
 */
public interface Api {


    //1.1登陆接口
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("app/user/login")
    Call<LoginBean>doLogin(
            @Body RequestBody json
    );



    //1.1测试接口（图片上传）
    @Multipart
    @POST("hotel-ai-admin/sys/common/uploadToOss")
    Call<ImgUpBean> doImgTest(
            @Part MultipartBody.Part file
    );

}
