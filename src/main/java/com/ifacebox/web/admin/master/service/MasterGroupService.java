package com.ifacebox.web.admin.master.service;

import com.ifacebox.web.admin.master.dao.MasterGroupDao;
import com.ifacebox.web.admin.master.dao.MasterGroupRoleDao;
import com.ifacebox.web.admin.master.model.MasterGroup;
import com.ifacebox.web.admin.master.model.MasterGroupRole;
import com.ifacebox.web.admin.master.model.MasterGroupStatus;
import com.ifacebox.web.common.model.UserAgent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author znn
 */
@Service
public class MasterGroupService {
    @Resource
    private MasterGroupDao masterGroupDao;
    @Resource
    private MasterGroupRoleDao masterGroupRoleDao;

    public List<MasterGroup> query() {
        List<MasterGroup> groupList = masterGroupDao.query();
        for (MasterGroup group : groupList) {
            group.setStatusExplain(this.getStatusExplain(group.getStatusId()));
            List<MasterGroupRole> groupRoleList = masterGroupRoleDao.queryByGroupId(group.getId());
            List<Integer> roleId = new ArrayList<>();
            for (MasterGroupRole groupRole : groupRoleList) {
                roleId.add(groupRole.getRoleId());
            }
            group.setRoleId(roleId.toArray(new Integer[0]));
        }
        return groupList;
    }

    public MasterGroup queryById(Integer id) {
        return masterGroupDao.queryById(id);
    }

    public MasterGroup queryByGroupName(String groupName) {
        return masterGroupDao.queryByGroupName(groupName);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(MasterGroup masterGroup, UserAgent userAgent) {
        masterGroup.setCreateUser(userAgent.getMasterUser().getUsername());
        if (!ObjectUtils.isEmpty(masterGroupDao.save(masterGroup))) {
            this.save(masterGroup);
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean edit(MasterGroup masterGroup, UserAgent userAgent) {
        masterGroup.setUpdateUser(userAgent.getMasterUser().getUsername());
        if (!ObjectUtils.isEmpty(masterGroupDao.edit(masterGroup))) {
            this.save(masterGroup);
            return true;
        }
        return false;
    }

    private void save(MasterGroup masterGroup) {
        masterGroupRoleDao.delete(masterGroup.getId());
        for (Integer roleId : masterGroup.getRoleId()) {
            MasterGroupRole groupRole = new MasterGroupRole();
            groupRole.setGroupId(masterGroup.getId());
            groupRole.setRoleId(roleId);
            masterGroupRoleDao.save(groupRole);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Integer id) {
        if (!ObjectUtils.isEmpty(masterGroupDao.delete(id))) {
            masterGroupRoleDao.delete(id);
            return true;
        }
        return false;
    }

    private String getStatusExplain(Integer statusId) {
        for (MasterGroupStatus groupStatus : MasterGroupStatus.values()) {
            if (groupStatus.getStatusId().equals(statusId)) {
                return groupStatus.getStatusExplain();
            }
        }
        return MasterGroupStatus.UNKNOWN.getStatusExplain();
    }
}
