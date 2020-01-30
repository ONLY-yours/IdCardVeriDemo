package com.arcsoft.idcardveridemo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @auther: lijunjie
 * @createDate: 2020/1/30  18:56
 * @purpose：用于弹窗使用
 */
public class ToastUtil {

    private static Toast toast = null;


    /**
     * @param context 内容器实体
     * @param text    提示文字内容
     */
    public static void toastShortShow(Context context, CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            toastShow(context, text, Toast.LENGTH_SHORT);
        }
    }

    /**
     * @param context   内容器实体
     * @param textResId 提示文字所在资源id
     */
    public static void toastShortShow(Context context, int textResId) {
        toastShow(context, textResId, Toast.LENGTH_SHORT);
    }

    /**
     * @param context   内容器实体
     * @param textResId 提示文字所在资源id
     * @param duration  提示时间
     */
    public static void toastShow(Context context, int textResId, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, textResId, duration);
        } else {
            toast.setText(textResId);
        }
        toast.show();
    }

    /**
     * @param context  内容器实体
     * @param msg      提示消息内容
     * @param duration 提示时间
     */
    public static void toastShow(Context context, CharSequence msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, duration);
        } else {
            toast.setText(msg);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
