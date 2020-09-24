package com.ifacebox.web.admin.master.service;

import com.ifacebox.web.admin.master.dao.MasterUserGroupDao;
import com.ifacebox.web.admin.master.model.MasterUserGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author znn
 */
@Service
public class MasterUserGroupService {
    @Resource
    private MasterUserGroupDao masterUserGroupDao;

    public List<MasterUserGroup> queryByGroupId(Integer groupId) {
        return masterUserGroupDao.queryByGroupId(groupId);
    }
}
