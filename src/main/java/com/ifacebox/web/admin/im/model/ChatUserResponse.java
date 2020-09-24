package com.ifacebox.web.admin.im.model;

import java.util.List;

/**
 * @author znn
 */
public class ChatUserResponse {
    private ChatUser mine;
    private List<ChatUserFriend> friend;
    private List<ChatUserGroup> group;

    public ChatUser getMine() {
        return mine;
    }

    public void setMine(ChatUser mine) {
        this.mine = mine;
    }

    public List<ChatUserFriend> getFriend() {
        return friend;
    }

    public void setFriend(List<ChatUserFriend> friend) {
        this.friend = friend;
    }

    public List<ChatUserGroup> getGroup() {
        return group;
    }

    public void setGroup(List<ChatUserGroup> group) {
        this.group = group;
    }
}
