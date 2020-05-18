package com.arcsoft.idcardveridemo.bean;

/**
 * @auther: lijunjie
 * @createDate: 2020/3/23  21:04
 * @purpose：
 */
public class LoginBean {

    private String success;
    private String message;
    private String code;
    private ResultBean result;
    private String timestamp;

    public String getCode() {
        return code;
    }

    public ResultBean getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getSuccess() {
        return success;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public static class ResultBean{
        public String token;
        public String username;
        public String realname;
        public String nickname;
        public String headimg;

        public String getToken() {
            return token;
        }

        public String getHeadimg() {
            return headimg;
        }

        public String getNickname() {
            return nickname;
        }

        public String getRealname() {
            return realname;
        }

        public String getUsername() {
            return username;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }


}
