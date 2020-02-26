package com.arcsoft.idcardveridemo.utils;

import android.os.Handler;

/**
 * @auther: lijunjie
 * @createDate: 2020/2/26  21:07
 * @purpose：计时（一段时间调用一次方法）
 */
class TimeUtil {

    /**
     * @purpose: 间隔1秒调用一次
     */
    Handler mHandler = new Handler();
    Runnable r = new Runnable() {

        @Override
        public void run() {
            //do something
            //每隔1s循环执行run方法
            mHandler.postDelayed(this, 1000);
        }
    };


}
