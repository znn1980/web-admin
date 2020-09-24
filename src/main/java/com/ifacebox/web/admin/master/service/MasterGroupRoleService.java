package com.ifacebox.web.admin.master.service;

import com.ifacebox.web.admin.master.dao.MasterGroupRoleDao;
import com.ifacebox.web.admin.master.model.MasterGroupRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author znn
 */
@Service
public class MasterGroupRoleService {
    @Resource
    private MasterGroupRoleDao masterGroupRoleDao;

    public List<MasterGroupRole> queryByRoleId(Integer roleId) {
        return masterGroupRoleDao.queryByRoleId(roleId);
    }
}
