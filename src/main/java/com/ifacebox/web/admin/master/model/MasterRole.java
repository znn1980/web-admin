package com.ifacebox.web.admin.master.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author znn
 */
public class MasterRole {
    private Integer id;
    @NotBlank(message = "角色名称不能为空！")
    @Size(min = 2, max = 64, message = "角色名称长度应在2-64之间！")
    private String roleName;
    @NotBlank(message = "角色说明不能为空！")
    @Size(min = 2, max = 255, message = "角色说明长度应在2-255之间！")
    private String roleExplain;
    @NotNull(message = "角色状态不能为空！")
    private Integer statusId;
    private String statusExplain;
    private String createUser;
    private String updateUser;
    private String createTime;
    private String updateTime;
    private Integer[] menuId;
    private Integer[] operateId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleExplain() {
        return roleExplain;
    }

    public void setRoleExplain(String roleExplain) {
        this.roleExplain = roleExplain;
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

    public Integer[] getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer[] menuId) {
        this.menuId = menuId;
    }

    public Integer[] getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer[] operateId) {
        this.operateId = operateId;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("编码=").append(id);
        sb.append(",名称=").append(roleName);
        sb.append(",说明=").append(roleExplain);
        sb.append(",状态=").append(statusId);
        sb.append(",创建用户=").append(createUser);
        sb.append(",修改用户=").append(updateUser);
        sb.append(",创建时间=").append(createTime);
        sb.append(",修改时间=").append(updateTime);
        return sb.toString();
    }
}
