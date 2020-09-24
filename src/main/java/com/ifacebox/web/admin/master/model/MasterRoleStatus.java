package com.ifacebox.web.admin.master.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author znn
 */
public enum MasterRoleStatus {
    /**
     * 状态
     */
    UNKNOWN(0, "未知"),
    ENABLE(1, "启用"),
    DISABLE(2, "禁用");
    private Integer statusId;
    private String statusExplain;

    MasterRoleStatus(Integer statusId, String statusExplain) {
        this.setStatusId(statusId);
        this.setStatusExplain(statusExplain);
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusExplain() {
        return statusExplain;
    }

    public void setStatusExplain(String statusExplain) {
        this.statusExplain = statusExplain;
    }

    public static List<Map<String, Object>> toArray() {
        List<Map<String, Object>> items = new ArrayList<>();
        for (MasterRoleStatus status : MasterRoleStatus.values()) {
            Map<String, Object> item = new HashMap<>();
            item.put("statusId", status.getStatusId());
            item.put("statusExplain", status.getStatusExplain());
            items.add(item);
        }
        return items;
    }
}
