package com.arcsoft.idcardveridemo.net;

import com.arcsoft.idcardveridemo.net.Client.NetRepository;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @auther: lijunjie
 * @createDate: 2020/3/22  12:54
 * @purpose：
 */
public class AppRetrofit {

    private static Api netApi;


    public static Api getNetApi() {
        if (netApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(NetRepository.getClient().build())
                    .baseUrl("http://121.199.77.184/") // 设置网络请求baseUrl
                    .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                    .build();
            netApi = retrofit.create(Api.class);
        }
        return netApi;
    }




    /**
     * 公共参数
     *
     * @param service
     * @param action
     * @param page
     * @param pageSize
     * @return
     */
    public static HashMap<String, String> getBaseHeaders(String service, String action,
                                                         int page, int pageSize) {

        HashMap<String, String> param = new HashMap<>();
        //具体字段看项目
//        if (UserInfoManager.getInstance().getNowUser() != null) {
//            param.put("token", UserInfoManager.getInstance().getNowUser().getToken());
//        }

        //        param.put("Content-type", "application/wocai");
        return param;
    }

    public static HashMap<String, String> getBaseHeaders(String service, String action) {
        return getBaseHeaders(service, action, 1,10);
    }
}
