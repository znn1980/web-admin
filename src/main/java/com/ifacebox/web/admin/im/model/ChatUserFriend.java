package com.ifacebox.web.admin.im.model;

import java.util.List;

/**
 * @author znn
 */
public class ChatUserFriend {
    private Integer id;
    private String groupname;
    private List<ChatUser> list;

    public ChatUserFriend(Integer id, String groupname, List<ChatUser> list) {
        this.id = id;
        this.groupname = groupname;
        this.list = list;
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

    public List<ChatUser> getList() {
        return list;
    }

    public void setList(List<ChatUser> list) {
        this.list = list;
    }
}
