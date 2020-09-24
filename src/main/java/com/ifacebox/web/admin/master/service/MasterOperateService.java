package com.ifacebox.web.admin.master.service;

import com.ifacebox.web.admin.master.dao.MasterOperateDao;
import com.ifacebox.web.admin.master.model.*;
import com.ifacebox.web.common.model.UserAgent;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author znn
 */
@Service
public class MasterOperateService {
    /**
     * 顶级菜单的上级菜单编码
     */
    public static final int TREE_ROOT = 0;
    @Resource
    private MasterOperateDao masterOperateDao;

    public MasterOperate queryById(Integer id) {
        return masterOperateDao.queryById(id);
    }

    public MasterOperate queryByTitle(String title) {
        return masterOperateDao.queryByTitle(title);
    }

    public List<MasterOperate> queryByParentId(Integer parentId) {
        return masterOperateDao.queryByParentId(parentId);
    }

    public List<MasterOperateTree> queryTree() {
        List<MasterOperateTree> masterOperateTreeList = this.queryTreeByParentId(TREE_ROOT);
        for (MasterOperateTree masterOperateTree : masterOperateTreeList) {
            masterOperateTree.setChildren(this.queryTreeByParentId(masterOperateTree.getId()));
        }
        return masterOperateTreeList;
    }

    private List<MasterOperateTree> queryTreeByParentId(Integer parentId) {
        List<MasterOperateTree> masterOperateTreeList = new ArrayList<>();
        List<MasterOperate> masterOperateList = this.queryByParentId(parentId);
        for (MasterOperate masterOperate : masterOperateList) {
            MasterOperateTree masterOperateTree = new MasterOperateTree();
            BeanUtils.copyProperties(masterOperate, masterOperateTree);
            masterOperateTree.setAttrExplain(this.getAttrExplain(masterOperateTree.getAttrId()));
            masterOperateTree.setStatusExplain(this.getStatusExplain(masterOperateTree.getStatusId()));
            masterOperateTree.setChildren(this.queryTreeByParentId(masterOperateTree.getId()));
            masterOperateTreeList.add(masterOperateTree);
        }
        return masterOperateTreeList;
    }

    public boolean save(MasterOperate masterOperate, UserAgent userAgent) {
        masterOperate.setCreateUser(userAgent.getMasterUser().getUsername());
        return !ObjectUtils.isEmpty(masterOperateDao.save(masterOperate));
    }

    public boolean edit(MasterOperate masterOperate, UserAgent userAgent) {
        masterOperate.setUpdateUser(userAgent.getMasterUser().getUsername());
        return !ObjectUtils.isEmpty(masterOperateDao.edit(masterOperate));
    }

    public boolean delete(Integer id) {
        return !ObjectUtils.isEmpty(masterOperateDao.delete(id));
    }

    private String getAttrExplain(Integer attrId) {
        for (MasterOperateAttr masterOperateAttr : MasterOperateAttr.values()) {
            if (masterOperateAttr.getAttrId().equals(attrId)) {
                return masterOperateAttr.getAttrExplain();
            }
        }
        return MasterOperateAttr.UNKNOWN.getAttrExplain();
    }

    private String getStatusExplain(Integer statusId) {
        for (MasterOperateStatus masterOperateStatus : MasterOperateStatus.values()) {
            if (masterOperateStatus.getStatusId().equals(statusId)) {
                return masterOperateStatus.getStatusExplain();
            }
        }
        return MasterOperateStatus.UNKNOWN.getStatusExplain();
    }
}
