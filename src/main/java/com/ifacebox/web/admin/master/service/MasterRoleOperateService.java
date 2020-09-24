package com.ifacebox.web.admin.master.service;

import com.ifacebox.web.admin.master.dao.MasterRoleOperateDao;
import com.ifacebox.web.admin.master.model.MasterRole;
import com.ifacebox.web.admin.master.model.MasterRoleOperate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author znn
 */
@Service
public class MasterRoleOperateService {
    @Resource
    private MasterRoleOperateDao masterRoleOperateDao;

    public List<MasterRoleOperate> queryByRoleId(Integer roleId) {
        return masterRoleOperateDao.queryByRoleId(roleId);
    }

    public List<MasterRoleOperate> queryByOperateId(Integer operateId) {
        return masterRoleOperateDao.queryByOperateId(operateId);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(MasterRole masterRole) {
        masterRoleOperateDao.delete(masterRole.getId());
        for (Integer operateId : masterRole.getOperateId()) {
            MasterRoleOperate roleOperate = new MasterRoleOperate();
            roleOperate.setRoleId(masterRole.getId());
            roleOperate.setOperateId(operateId);
            masterRoleOperateDao.save(roleOperate);
        }
        return true;
    }
}
