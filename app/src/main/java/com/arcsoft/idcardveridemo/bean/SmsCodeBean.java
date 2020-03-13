package com.arcsoft.idcardveridemo.bean;

import java.io.Serializable;

/**
 * 验证码
 * @auther: xiezuoping
 * @createDate: 2019/8/11
 */
public class SmsCodeBean implements Serializable {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
