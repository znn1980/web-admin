package com.ifacebox.web.admin.master.service;

import com.ifacebox.web.admin.im.dao.ChatUserDao;
import com.ifacebox.web.admin.im.model.ChatUser;
import com.ifacebox.web.admin.im.model.ChatUserStatus;
import com.ifacebox.web.admin.im.service.ChatUserService;
import com.ifacebox.web.admin.master.dao.MasterUserDao;
import com.ifacebox.web.admin.master.dao.MasterUserGroupDao;
import com.ifacebox.web.admin.master.model.*;
import com.ifacebox.web.common.model.UserAgent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author znn
 */
@Service
public class MasterUserService {
    public static final String ADMIN_NAME = "admin";
    public static final String ADMIN_SESSION = "admin_session";
    public static final String ADMIN_CAPTCHA_SESSION = "admin_captcha_session";
    @Resource
    private MasterUserDao masterUserDao;
    @Resource
    private MasterUserGroupDao masterUserGroupDao;
    @Resource
    private ChatUserDao chatUserDao;

    public List<MasterUser> queryByAll() {
        return masterUserDao.queryByAll();
    }

    public List<MasterUser> query(MasterUserQuery masterUserQuery) {
        List<MasterUser> userList = masterUserDao.query(masterUserQuery);
        for (MasterUser masterUser : userList) {
            masterUser.setAttrExplain(this.getAttrExplain(masterUser.getAttrId()));
            masterUser.setStatusExplain(this.getStatusExplain(masterUser.getStatusId()));
            List<MasterUserGroup> userGroupList = masterUserGroupDao.queryByUserId(masterUser.getId());
            List<Integer> groupId = new ArrayList<>();
            for (MasterUserGroup userGroup : userGroupList) {
                groupId.add(userGroup.getGroupId());
            }
            masterUser.setGroupId(groupId.toArray(new Integer[0]));
        }
        return userList;
    }

    public Long total(MasterUserQuery masterUserQuery) {
        return masterUserDao.total(masterUserQuery);
    }

    public MasterUser queryById(Integer id) {
        return masterUserDao.queryById(id);
    }

    public MasterUser queryByUsername(String username) {
        return masterUserDao.queryByUsername(username);
    }

    public MasterUser queryByMobile(String mobile) {
        return masterUserDao.queryByMobile(mobile);
    }

    public MasterUser queryByEmail(String email) {
        return masterUserDao.queryByEmail(email);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(MasterUser masterUser, UserAgent userAgent) {
        masterUser.setCreateUser(userAgent.getMasterUser().getUsername());
        if (!ObjectUtils.isEmpty(masterUserDao.save(masterUser))) {
            this.saveUserGroup(masterUser);
            this.saveChatUser(masterUser);
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean edit(MasterUser masterUser, UserAgent userAgent) {
        masterUser.setUpdateUser(userAgent.getMasterUser().getUsername());
        if (!ObjectUtils.isEmpty(masterUserDao.edit(masterUser))) {
            this.saveUserGroup(masterUser);
            this.saveChatUser(masterUser);
            return true;
        }
        return false;
    }

    private void saveUserGroup(MasterUser masterUser) {
        masterUserGroupDao.delete(masterUser.getId());
        for (Integer groupId : masterUser.getGroupId()) {
            MasterUserGroup userGroup = new MasterUserGroup();
            userGroup.setUserId(masterUser.getId());
            userGroup.setGroupId(groupId);
            masterUserGroupDao.save(userGroup);
        }
    }

    private void saveChatUser(MasterUser masterUser) {
        ChatUser chatUser = new ChatUser();
        chatUser.setId(masterUser.getId());
        chatUser.setUsername(masterUser.getUsername() + "（" + masterUser.getName() + "）");
        if (ObjectUtils.isEmpty(chatUserDao.queryById(masterUser.getId()))) {
            chatUser.setAvatar(ChatUserService.AVATAR);
            chatUser.setStatus(ChatUserStatus.online.name());
            chatUserDao.save(chatUser);
        } else {
            chatUserDao.edit(chatUser);
        }
    }

    public boolean editPassword(MasterUser masterUser) {
        return !ObjectUtils.isEmpty(masterUserDao.editPassword(masterUser));
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Integer id) {
        if (!ObjectUtils.isEmpty(masterUserDao.delete(id))) {
            masterUserGroupDao.delete(id);
            chatUserDao.delete(id);
            return true;
        }
        return false;
    }

    public MasterUser login(MasterUserLogin masterUserLogin, UserAgent userAgent) {
        MasterUser masterUser = masterUserDao.queryByUsernameAndPassword(masterUserLogin.getUsername(), masterUserLogin.getPassword());
        if (ObjectUtils.isEmpty(masterUser)) {
            masterUser = masterUserDao.queryByMobileAndPassword(masterUserLogin.getUsername(), masterUserLogin.getPassword());
        }
        if (ObjectUtils.isEmpty(masterUser)) {
            masterUser = masterUserDao.queryByEmailAndPassword(masterUserLogin.getUsername(), masterUserLogin.getPassword());
        }
        if (!ObjectUtils.isEmpty(masterUser)) {
            userAgent.setMasterUser(masterUser);
            masterUser.setLoginIp(userAgent.getIp());
            masterUserDao.editLogin(masterUser);
        }
        return masterUser;
    }

    public void setCaptcha(HttpSession session, String captcha) {
        session.setAttribute(MasterUserService.ADMIN_CAPTCHA_SESSION, captcha);
    }

    public boolean isCaptcha(HttpSession session, String captcha) {
        Object oldCaptcha = session.getAttribute(MasterUserService.ADMIN_CAPTCHA_SESSION);
        if (!ObjectUtils.isEmpty(oldCaptcha) && ((String) oldCaptcha).equalsIgnoreCase(captcha)) {
            session.removeAttribute(MasterUserService.ADMIN_CAPTCHA_SESSION);
            return true;
        }
        return false;
    }

    private String getAttrExplain(Integer attrId) {
        for (MasterUserAttr menuBarAttr : MasterUserAttr.values()) {
            if (menuBarAttr.getAttrId().equals(attrId)) {
                return menuBarAttr.getAttrExplain();
            }
        }
        return MasterMenuAttr.UNKNOWN.getAttrExplain();
    }

    private String getStatusExplain(Integer statusId) {
        for (MasterUserStatus menuBarStatus : MasterUserStatus.values()) {
            if (menuBarStatus.getStatusId().equals(statusId)) {
                return menuBarStatus.getStatusExplain();
            }
        }
        return MasterMenuStatus.UNKNOWN.getStatusExplain();
    }
}
