package com.arcsoft.idcardveridemo.net.Client;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @auther: lijunjie
 * @createDate: 2020/4/17  19:11
 * @purpose：
 */
public class NetRepository {

    public static OkHttpClient.Builder getClient(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        httpClientBuilder.addNetworkInterceptor(new HeaderTokenIntercept());
        return httpClientBuilder;
    }

}
