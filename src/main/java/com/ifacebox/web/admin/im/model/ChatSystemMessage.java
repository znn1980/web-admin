package com.ifacebox.web.admin.im.model;

/**
 * @author znn
 */
public class ChatSystemMessage {
    private boolean system;
    private Integer id;
    private ChatToMessageType type;
    private String content;

    public ChatSystemMessage(Integer id, ChatToMessageType type, String content) {
        this.system = true;
        this.id = id;
        this.type = type;
        this.content = content;
    }

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ChatToMessageType getType() {
        return type;
    }

    public void setType(ChatToMessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
