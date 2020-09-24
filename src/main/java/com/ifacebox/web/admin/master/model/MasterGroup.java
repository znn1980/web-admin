package com.ifacebox.web.admin.master.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author znn
 */
public class MasterGroup {
    private Integer id;
    @NotBlank(message = "分组名称不能为空！")
    @Size(min = 2, max = 64, message = "分组名称长度应在2-64之间！")
    private String groupName;
    @NotBlank(message = "分组说明不能为空！")
    @Size(min = 2, max = 255, message = "分组说明长度应在2-255之间！")
    private String groupExplain;
    @NotNull(message = "分组状态不能为空！")
    private Integer statusId;
    private String statusExplain;
    private String createUser;
    private String updateUser;
    private String createTime;
    private String updateTime;
    private Integer[] roleId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupExplain() {
        return groupExplain;
    }

    public void setGroupExplain(String groupExplain) {
        this.groupExplain = groupExplain;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer[] getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer[] roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("编码=").append(id);
        sb.append(",名称=").append(groupName);
        sb.append(",说明=").append(groupExplain);
        sb.append(",状态=").append(statusId);
        sb.append(",创建用户=").append(createUser);
        sb.append(",修改用户=").append(updateUser);
        sb.append(",创建时间=").append(createTime);
        sb.append(",修改时间=").append(updateTime);
        return sb.toString();
    }
}
