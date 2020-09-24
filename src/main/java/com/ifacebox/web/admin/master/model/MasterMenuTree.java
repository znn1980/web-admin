package com.ifacebox.web.admin.master.model;

import java.util.List;

/**
 * @author znn
 */
public class MasterMenuTree extends MasterMenu {
    private List<MasterMenuTree> children;

    public List<MasterMenuTree> getChildren() {
        return children;
    }

    public void setChildren(List<MasterMenuTree> children) {
        this.children = children;
    }
}
