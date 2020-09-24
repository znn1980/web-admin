package com.ifacebox.web.admin.im.model;

/**
 * @author znn
 */
public class ChatUserGroup {
    private Integer id;
    private String groupname;
    private String avatar;

    public ChatUserGroup(Integer id, String groupname,String avatar) {
        this.id = id;
        this.groupname = groupname;
        this.avatar = avatar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
