package com.ifacebox.web.admin.master.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author znn
 */
public enum MasterUserAttr {
    /**
     * 属性
     */
    UNKNOWN(0, "未知"),
    ADMIN(1, "管理员"),
    USER(2, "用户");
    private Integer attrId;
    private String attrExplain;

    MasterUserAttr(Integer attrId, String attrExplain) {
        this.setAttrId(attrId);
        this.setAttrExplain(attrExplain);
    }

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public String getAttrExplain() {
        return attrExplain;
    }

    public void setAttrExplain(String attrExplain) {
        this.attrExplain = attrExplain;
    }

    public static List<Map<String, Object>> toArray() {
        List<Map<String, Object>> items = new ArrayList<>();
        for (MasterUserAttr attr : MasterUserAttr.values()) {
            Map<String, Object> item = new HashMap<>();
            item.put("attrId", attr.getAttrId());
            item.put("attrExplain", attr.getAttrExplain());
            items.add(item);
        }
        return items;
    }
}
