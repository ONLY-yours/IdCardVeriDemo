package com.arcsoft.idcardveridemo.net.Client;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @auther: lijunjie
 * @createDate: 2020/4/17  19:05
 * @purpose：用于添加token
 */
public class HeaderTokenIntercept implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
//        String token = "iWAoOI4rrrvRISluC7I/nKmhTIvDfRACU+iaaRWsKcCUIPio58RcRZvhZKKG6TsM";
//        String token = LoadingActivity.token;
        String token = "";
        if (token.isEmpty()) {
            Request originalRequest = chain.request();
            return chain.proceed(originalRequest);
        }else {
            Request originalRequest = chain.request();
            //key的话以后台给的为准，我这边是叫token
            Request updateRequest = originalRequest.newBuilder().header("X-Access-Token", token).build();
            return chain.proceed(updateRequest);
        }
    }
}
