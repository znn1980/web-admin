package com.ifacebox.web.common.model;

/**
 * @author znn
 */
public class BaseResponse {
    protected Integer code;
    protected String message;
    protected Boolean success;

    public BaseResponse(Integer code, String message) {
        this.setCode(code);
        this.setMessage(message);
        this.setSuccess(StatusResponse.SUCCESS.getStatusCode().equals(code));
    }

    public BaseResponse(StatusResponse statusResponse) {
        this(statusResponse.getStatusCode(), statusResponse.getStatusMessage());
    }

    public BaseResponse(StatusResponse statusResponse, String message) {
        this(statusResponse.getStatusCode(), message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
