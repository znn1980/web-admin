package com.ifacebox.web.admin.im.controller;

import com.ifacebox.web.admin.controller.BaseBodyController;
import com.ifacebox.web.admin.im.model.*;
import com.ifacebox.web.admin.im.service.ChatUserService;
import com.ifacebox.web.common.annotation.UserAgentRequest;
import com.ifacebox.web.common.model.BaseResponse;
import com.ifacebox.web.common.model.StatusResponse;
import com.ifacebox.web.common.model.UserAgent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author znn
 */
@Controller
public class ChatUserController extends BaseBodyController {
    @Resource
    private ChatUserService chatUserService;

    @PutMapping("/admin/im/chat/user/edit.json")
    @ResponseBody
    public BaseResponse edit(@UserAgentRequest UserAgent userAgent, @RequestBody ChatUser chatUser) {
        if (!permissionManager.hasLogin(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        chatUser.setId(userAgent.getMasterUser().getId());
        if (!chatUserService.edit(chatUser)) {
            return new BaseResponse(StatusResponse.UPDATE_FAIL);
        }
        return new BaseResponse(StatusResponse.SUCCESS);
    }

    @GetMapping("/admin/im/chat/user/list.json")
    @ResponseBody
    public ChatBaseResponse<ChatUserResponse> list(@UserAgentRequest UserAgent userAgent) {
        if (!permissionManager.hasLogin(userAgent)) {
            return new ChatBaseResponse<>(StatusResponse.LOGOUT);
        }
        ChatUserResponse response = new ChatUserResponse();
        response.setMine(chatUserService.queryById(userAgent.getMasterUser().getId()));
        List<ChatUserFriend> friendList = new ArrayList<>();
        friendList.add(new ChatUserFriend(ChatUserService.GROUP_ID, ChatUserService.GROUP_NAME, chatUserService.query()));
        response.setFriend(friendList);
        List<ChatUserGroup> groupList = new ArrayList<>();
        groupList.add(new ChatUserGroup(ChatUserService.GROUP_ID, ChatUserService.GROUP_NAME, ChatUserService.AVATAR));
        response.setGroup(groupList);
        return new ChatBaseResponse(StatusResponse.SUCCESS, response);
    }

    @GetMapping("/admin/im/chat/user/members.json")
    @ResponseBody
    public ChatBaseResponse<ChatMembersResponse> members(@UserAgentRequest UserAgent userAgent, @RequestParam("id") Integer id) {
        if (!permissionManager.hasLogin(userAgent)) {
            return new ChatBaseResponse<>(StatusResponse.LOGOUT);
        }
        ChatMembersResponse response = new ChatMembersResponse();
        response.setList(chatUserService.query());
        return new ChatBaseResponse(StatusResponse.SUCCESS, response);
    }
}
