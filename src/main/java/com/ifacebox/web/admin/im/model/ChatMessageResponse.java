package com.ifacebox.web.admin.im.model;

/**
 * @author znn
 */
public class ChatMessageResponse<T> {
    private ChatMessageType type;
    private T data;

    public ChatMessageType getType() {
        return type;
    }

    public void setType(ChatMessageType type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
