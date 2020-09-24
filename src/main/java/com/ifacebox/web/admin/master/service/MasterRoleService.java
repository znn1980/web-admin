package com.ifacebox.web.admin.master.service;

import com.ifacebox.web.admin.master.dao.MasterRoleDao;
import com.ifacebox.web.admin.master.dao.MasterRoleMenuDao;
import com.ifacebox.web.admin.master.dao.MasterRoleOperateDao;
import com.ifacebox.web.admin.master.model.*;
import com.ifacebox.web.common.model.UserAgent;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author znn
 */
@Service
public class MasterRoleService {
    @Resource
    private MasterRoleDao masterRoleDao;
    @Resource
    private MasterRoleMenuDao masterRoleMenuDao;
    @Resource
    private MasterRoleOperateDao masterRoleOperateDao;

    public List<MasterRole> query() {
        List<MasterRole> roleList = masterRoleDao.query();
        for (MasterRole role : roleList) {
            role.setStatusExplain(this.getStatusExplain(role.getStatusId()));
            List<MasterRoleMenu> roleMenuList = masterRoleMenuDao.queryByRoleId(role.getId());
            List<MasterRoleOperate> roleOperateList = masterRoleOperateDao.queryByRoleId(role.getId());
            List<Integer> menuId = new ArrayList<>();
            List<Integer> operateId = new ArrayList<>();
            for (MasterRoleMenu roleMenu : roleMenuList) {
                menuId.add(roleMenu.getMenuId());
            }
            for (MasterRoleOperate roleOperate : roleOperateList) {
                operateId.add(roleOperate.getOperateId());
            }
            role.setMenuId(menuId.toArray(new Integer[0]));
            role.setOperateId(operateId.toArray(new Integer[0]));
        }
        return roleList;
    }

    public MasterRole queryById(Integer id) {
        return masterRoleDao.queryById(id);
    }

    public MasterRole queryByRoleName(String roleName) {
        return masterRoleDao.queryByRoleName(roleName);
    }

    public boolean save(MasterRole masterRole, UserAgent userAgent) {
        masterRole.setCreateUser(userAgent.getMasterUser().getUsername());
        return !ObjectUtils.isEmpty(masterRoleDao.save(masterRole));
    }

    public boolean edit(MasterRole masterRole, UserAgent userAgent) {
        masterRole.setUpdateUser(userAgent.getMasterUser().getUsername());
        return !ObjectUtils.isEmpty(masterRoleDao.edit(masterRole));
    }

    public boolean delete(Integer id) {
        return !ObjectUtils.isEmpty(masterRoleDao.delete(id));
    }

    private String getStatusExplain(Integer statusId) {
        for (MasterRoleStatus roleStatus : MasterRoleStatus.values()) {
            if (roleStatus.getStatusId().equals(statusId)) {
                return roleStatus.getStatusExplain();
            }
        }
        return MasterRoleStatus.UNKNOWN.getStatusExplain();
    }
}
