package com.ifacebox.web.admin.im.model;

import java.util.List;

/**
 * @author znn
 */
public class ChatMembersResponse {
    private List<ChatUser> list;

    public List<ChatUser> getList() {
        return list;
    }

    public void setList(List<ChatUser> list) {
        this.list = list;
    }
}
