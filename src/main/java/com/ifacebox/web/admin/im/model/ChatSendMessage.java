package com.ifacebox.web.admin.im.model;

/**
 * @author znn
 */
public class ChatSendMessage {
    private ChatMineMessage mine;
    private ChatToMessage to;

    public ChatMineMessage getMine() {
        return mine;
    }

    public void setMine(ChatMineMessage mine) {
        this.mine = mine;
    }

    public ChatToMessage getTo() {
        return to;
    }

    public void setTo(ChatToMessage to) {
        this.to = to;
    }
}
