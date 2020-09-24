package com.ifacebox.web.admin.im.model;

/**
 * @author znn
 */
public class ChatMessageRequest {
    private ChatMessageType type;
    private ChatSendMessage data;

    public ChatMessageType getType() {
        return type;
    }

    public void setType(ChatMessageType type) {
        this.type = type;
    }

    public ChatSendMessage getData() {
        return data;
    }

    public void setData(ChatSendMessage data) {
        this.data = data;
    }
}
