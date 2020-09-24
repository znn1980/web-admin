package com.ifacebox.web.common.model;

/**
 * @author znn
 */
public enum StatusResponse {
    /**
     * 公共响应状态
     */
    SUCCESS(0, "成功"),
    LOGOUT(1001, "未登录或没有权限"),
    TIMEOUT(1002, "超时"),
    SAVE_FAIL(2001, "保存失败"),
    UPDATE_FAIL(2002, "修改失败"),
    DELETE_FAIL(2003, "删除失败"),
    VALID_FAIL(9000, "验证失败"),
    EXCEPTION(9990, "异常"),
    UNKNOWN(9999, "未知");
    private Integer statusCode;
    private String statusMessage;

    StatusResponse(Integer statusCode, String statusMessage) {
        this.setStatusCode(statusCode);
        this.setStatusMessage(statusMessage);
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

}
