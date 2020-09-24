package com.ifacebox.web.admin.master.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author znn
 */
public enum MasterOperateAttr {
    /**
     * 属性
     */
    UNKNOWN(0, "未知"),
    JSON(1, "JSON");
    private Integer attrId;
    private String attrExplain;

    MasterOperateAttr(Integer attrId, String attrExplain) {
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
        for (MasterOperateAttr attr : MasterOperateAttr.values()) {
            Map<String, Object> item = new HashMap<>();
            item.put("attrId", attr.getAttrId());
            item.put("attrExplain", attr.getAttrExplain());
            items.add(item);
        }
        return items;
    }
}
