package com.arcsoft.idcardveridemo.bean;

/**
 * @auther: lijunjie
 * @createDate: 2020/5/13  20:48
 * @purpose：
 */
public class ImgUpBean {

    private String success;
    private String message;
    private String code;
    private ResultBean result;
    private String timestamp;

    public String getMessage() {
        return message;
    }

    public String getSuccess() {
        return success;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getCode() {
        return code;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class ResultBean{
        public String filePath;
        public String realPath;

        public String getFilePath() {
            return filePath;
        }

        public String getRealPath() {
            return realPath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public void setRealPath(String realPath) {
            this.realPath = realPath;
        }
    }

}
