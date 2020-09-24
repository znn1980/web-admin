package com.ifacebox.web.admin.master.service;

import com.ifacebox.web.admin.master.dao.MasterRoleMenuDao;
import com.ifacebox.web.admin.master.model.MasterRole;
import com.ifacebox.web.admin.master.model.MasterRoleMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author znn
 */
@Service
public class MasterRoleMenuService {
    @Resource
    private MasterRoleMenuDao masterRoleMenuDao;

    public List<MasterRoleMenu> queryByRoleId(Integer roleId) {
        return masterRoleMenuDao.queryByRoleId(roleId);
    }

    public List<MasterRoleMenu> queryByMenuId(Integer menuId) {
        return masterRoleMenuDao.queryByMenuId(menuId);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(MasterRole masterRole) {
        masterRoleMenuDao.delete(masterRole.getId());
        for (Integer menuId : masterRole.getMenuId()) {
            MasterRoleMenu roleMenu = new MasterRoleMenu();
            roleMenu.setRoleId(masterRole.getId());
            roleMenu.setMenuId(menuId);
            masterRoleMenuDao.save(roleMenu);
        }
        return true;
    }
}
