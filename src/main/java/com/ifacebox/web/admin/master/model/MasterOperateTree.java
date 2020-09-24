package com.ifacebox.web.admin.master.model;

import java.util.List;

/**
 * @author znn
 */
public class MasterOperateTree extends MasterOperate {
    private List<MasterOperateTree> children;

    public List<MasterOperateTree> getChildren() {
        return children;
    }

    public void setChildren(List<MasterOperateTree> children) {
        this.children = children;
    }

}
