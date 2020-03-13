package com.arcsoft.idcardveridemo.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 服务端返回数据通用json格式
 * Created by HuYayu on 17/8/28.
 */

public class BaseBean<T> implements Serializable {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("data")
    @Expose
    private T data;
    @SerializedName("result")
    @Expose
    private T result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("msgtype")
    @Expose
    private String msgtype;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
