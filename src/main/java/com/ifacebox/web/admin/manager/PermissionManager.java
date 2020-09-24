package com.ifacebox.web.admin.manager;

import com.ifacebox.web.admin.master.dao.MasterMenuDao;
import com.ifacebox.web.admin.master.dao.MasterOperateDao;
import com.ifacebox.web.admin.master.model.*;
import com.ifacebox.web.admin.master.service.MasterUserService;
import com.ifacebox.web.common.model.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


/**
 * @author znn
 */
@Component
public class PermissionManager {
    private static final Logger logger = LoggerFactory.getLogger(PermissionManager.class);
    @Resource
    private MasterMenuDao masterMenuDao;
    @Resource
    private MasterOperateDao masterOperateDao;

    public String hasPassword(String password) {
        //此处可以使用MD5加密
        //DigestUtils.md5DigestAsHex(password.getBytes());
        return "$" + password + "$";
    }

    public boolean hasLogin(UserAgent userAgent) {
        return !ObjectUtils.isEmpty(userAgent)
                && !ObjectUtils.isEmpty(userAgent.getMasterUser())
                && !StringUtils.isEmpty(userAgent.getMethod())
                && !StringUtils.isEmpty(userAgent.getUrl());
    }

    public boolean hasPermission(UserAgent userAgent) {
        if (!this.hasLogin(userAgent)) {
            return false;
        }
        if (MasterUserService.ADMIN_NAME.equals(userAgent.getMasterUser().getUsername())) {
            return true;
        }
        if (this.hasMenuPermission(userAgent, userAgent.getMasterUser())) {
            return true;
        }
        if (this.hasOperatePermission(userAgent, userAgent.getMasterUser())) {
            return true;
        }
        return false;
    }

    private boolean hasMenuPermission(UserAgent userAgent, MasterUser masterUser) {
        PathMatcher matcher = new AntPathMatcher();
        for (MasterMenu masterMenu : masterMenuDao.queryByUserId(masterUser.getId())) {
            if (logger.isDebugEnabled()) {
                logger.debug("匹配{}:{}访问菜单{}权限", userAgent.getMethod(), userAgent.getUrl(), masterMenu.getAddress());
            }
            if (!StringUtils.isEmpty(masterMenu.getAddress())
                    && matcher.match(userAgent.getUrl(), masterMenu.getAddress())
                    && MasterMenuStatus.ENABLE.getStatusId().equals(masterMenu.getStatusId())) {
                return true;
            }
        }
        return false;
    }

    private boolean hasOperatePermission(UserAgent userAgent, MasterUser masterUser) {
        PathMatcher matcher = new AntPathMatcher();
        for (MasterOperate masterOperate : masterOperateDao.queryByUserId(masterUser.getId())) {
            if (logger.isDebugEnabled()) {
                logger.debug("匹配{}:{}访问操作{}:{}权限", userAgent.getMethod(), userAgent.getUrl(), masterOperate.getMethod(), masterOperate.getAddress());
            }
            if (!StringUtils.isEmpty(masterOperate.getAddress())
                    && matcher.match(userAgent.getUrl(), masterOperate.getAddress())
                    && userAgent.getMethod().equalsIgnoreCase(masterOperate.getMethod())
                    && MasterOperateStatus.ENABLE.getStatusId().equals(masterOperate.getStatusId())) {
                return true;
            }
        }
        return false;
    }
}
