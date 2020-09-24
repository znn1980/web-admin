package com.ifacebox.web.admin.im.model;

import com.ifacebox.web.common.model.StatusResponse;

/**
 * @author znn
 */
public class ChatBaseResponse<T> {
    private Integer code;
    private String msg;
    private T data;

    public ChatBaseResponse(Integer code, String msg) {
        this.setCode(code);
        this.setMsg(msg);
    }

    public ChatBaseResponse(StatusResponse statusResponse) {
        this(statusResponse.getStatusCode(), statusResponse.getStatusMessage());
    }

    public ChatBaseResponse(StatusResponse statusResponse, T data) {
        this(statusResponse.getStatusCode(), statusResponse.getStatusMessage());
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
