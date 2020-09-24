package com.ifacebox.web.admin.im.service;

import com.ifacebox.web.admin.im.dao.ChatUserDao;
import com.ifacebox.web.admin.im.model.ChatUser;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author znn
 */
@Service
public class ChatUserService {
    public static final Integer GROUP_ID = -1;
    public static final String GROUP_NAME = "全部";
    public static final String AVATAR = "../../views/admin/img/logo_32x32.png";
    @Resource
    private ChatUserDao chatUserDao;

    public List<ChatUser> query() {
        return chatUserDao.query();
    }

    public ChatUser queryById(Integer id) {
        return chatUserDao.queryById(id);
    }

    public boolean edit(ChatUser chatUser) {
        return !ObjectUtils.isEmpty(chatUserDao.edit(chatUser));
    }
}
