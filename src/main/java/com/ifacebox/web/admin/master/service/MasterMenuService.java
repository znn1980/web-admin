package com.ifacebox.web.admin.master.service;

import com.ifacebox.web.admin.master.dao.MasterMenuDao;
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
public class MasterMenuService {
    /**
     * 顶级菜单的上级菜单编码
     */
    public static final int TREE_ROOT = 0;
    @Resource
    private MasterMenuDao masterMenuDao;

    public MasterMenu queryById(Integer id) {
        return masterMenuDao.queryById(id);
    }

    public MasterMenu queryByTitle(String title) {
        return masterMenuDao.queryByTitle(title);
    }

    public List<MasterMenu> queryByParentId(Integer parentId) {
        return masterMenuDao.queryByParentId(parentId);
    }

    public List<MasterMenu> queryByParentIdAndUserId(Integer parentId, Integer userId) {
        return masterMenuDao.queryByParentIdAndUserId(parentId, userId);
    }

    public List<MasterMenuTree> queryTree() {
        List<MasterMenuTree> masterMenuTreeList = this.queryTreeByParentId(TREE_ROOT);
        for (MasterMenuTree masterMenuTree : masterMenuTreeList) {
            masterMenuTree.setChildren(this.queryTreeByParentId(masterMenuTree.getId()));
        }
        return masterMenuTreeList;
    }

    public List<MasterMenuTree> queryTreeByUserId(Integer userId) {
        List<MasterMenuTree> masterMenuTreeList = this.queryTreeByParentIdAndUserId(TREE_ROOT, userId);
        for (MasterMenuTree masterMenuTree : masterMenuTreeList) {
            masterMenuTree.setChildren(this.queryTreeByParentIdAndUserId(masterMenuTree.getId(), userId));
        }
        return masterMenuTreeList;
    }

    private List<MasterMenuTree> queryTreeByParentId(Integer parentId) {
        List<MasterMenuTree> masterMenuTreeList = new ArrayList<>();
        List<MasterMenu> masterMenuList = this.queryByParentId(parentId);
        for (MasterMenu masterMenu : masterMenuList) {
            MasterMenuTree masterMenuTree = new MasterMenuTree();
            BeanUtils.copyProperties(masterMenu, masterMenuTree);
            masterMenuTree.setAttrExplain(this.getAttrExplain(masterMenuTree.getAttrId()));
            masterMenuTree.setStatusExplain(this.getStatusExplain(masterMenuTree.getStatusId()));
            masterMenuTree.setChildren(this.queryTreeByParentId(masterMenuTree.getId()));
            masterMenuTreeList.add(masterMenuTree);
        }
        return masterMenuTreeList;
    }

    private List<MasterMenuTree> queryTreeByParentIdAndUserId(Integer parentId, Integer userId) {
        List<MasterMenuTree> masterMenuTreeList = new ArrayList<>();
        List<MasterMenu> masterMenuList = this.queryByParentIdAndUserId(parentId, userId);
        for (MasterMenu masterMenu : masterMenuList) {
            MasterMenuTree masterMenuTree = new MasterMenuTree();
            BeanUtils.copyProperties(masterMenu, masterMenuTree);
            masterMenuTree.setAttrExplain(this.getAttrExplain(masterMenuTree.getAttrId()));
            masterMenuTree.setStatusExplain(this.getStatusExplain(masterMenuTree.getStatusId()));
            masterMenuTree.setChildren(this.queryTreeByParentIdAndUserId(masterMenuTree.getId(), userId));
            masterMenuTreeList.add(masterMenuTree);
        }
        return masterMenuTreeList;
    }

    public boolean save(MasterMenu masterMenu, UserAgent userAgent) {
        masterMenu.setCreateUser(userAgent.getMasterUser().getUsername());
        return !ObjectUtils.isEmpty(masterMenuDao.save(masterMenu));
    }

    public boolean edit(MasterMenu masterMenu, UserAgent userAgent) {
        masterMenu.setUpdateUser(userAgent.getMasterUser().getUsername());
        return !ObjectUtils.isEmpty(masterMenuDao.edit(masterMenu));
    }

    public boolean delete(Integer id) {
        return !ObjectUtils.isEmpty(masterMenuDao.delete(id));
    }

    private String getAttrExplain(Integer attrId) {
        for (MasterMenuAttr menuBarAttr : MasterMenuAttr.values()) {
            if (menuBarAttr.getAttrId().equals(attrId)) {
                return menuBarAttr.getAttrExplain();
            }
        }
        return MasterMenuAttr.UNKNOWN.getAttrExplain();
    }

    private String getStatusExplain(Integer statusId) {
        for (MasterMenuStatus menuBarStatus : MasterMenuStatus.values()) {
            if (menuBarStatus.getStatusId().equals(statusId)) {
                return menuBarStatus.getStatusExplain();
            }
        }
        return MasterMenuStatus.UNKNOWN.getStatusExplain();
    }
}
