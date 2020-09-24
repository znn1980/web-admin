package com.ifacebox.web.admin.im.model;

/**
 * @author znn
 */
public class ChatToMessage {
    private Integer id;
    private String username;
    private String groupname;
    private String avatar;
    private String name;
    private String sign;
    private ChatUserStatus status;
    private ChatToMessageType type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public ChatUserStatus getStatus() {
        return status;
    }

    public void setStatus(ChatUserStatus status) {
        this.status = status;
    }

    public ChatToMessageType getType() {
        return type;
    }

    public void setType(ChatToMessageType type) {
        this.type = type;
    }
}
