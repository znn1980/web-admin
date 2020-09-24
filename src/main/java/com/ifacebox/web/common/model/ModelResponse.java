package com.ifacebox.web.common.model;

/**
 * @author znn
 */
public class ModelResponse<T> extends BaseResponse {
    private Long total;
    private T data;

    public ModelResponse(StatusResponse statusResponse) {
        super(statusResponse);
    }

    public ModelResponse(StatusResponse statusResponse, String message) {
        super(statusResponse, message);
    }

    public ModelResponse(StatusResponse statusResponse, T data) {
        super(statusResponse);
        this.setData(data);
    }

    public ModelResponse(StatusResponse statusResponse, Long total, T data) {
        super(statusResponse);
        this.setTotal(total);
        this.setData(data);
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
