package com.ifacebox.web.admin.master.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author znn
 */
public class MasterMenu {
    protected Integer id;
    @NotNull(message = "上级菜单不能为空！")
    protected Integer parentId;
    @NotBlank(message = "菜单标题不能为空！")
    @Size(min = 2, max = 64, message = "菜单标题长度应在2-64之间！")
    protected String title;
    @Size(min = 0, max = 255, message = "菜单地址长度不能大于255！")
    protected String address;
    @NotNull(message = "菜单属性不能为空！")
    protected Integer attrId;
    protected String attrExplain;
    @Size(min = 0, max = 255, message = "菜单图标长度不能大于255！")
    protected String iconCls;
    @NotNull(message = "菜单状态不能为空！")
    protected Integer statusId;
    protected String statusExplain;
    @NotNull(message = "菜单排序不能为空！")
    @Max(value = 9999, message = "菜单排序不能大于9999！")
    protected Integer orderId;
    protected String createUser;
    protected String updateUser;
    protected String createTime;
    protected String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("编码=").append(id);
        sb.append(",父编码=").append(parentId);
        sb.append(",标题=").append(title);
        sb.append(",地址=").append(address);
        sb.append(",属性=").append(attrId);
        sb.append(",图标=").append(iconCls);
        sb.append(",状态=").append(statusId);
        sb.append(",排序=").append(orderId);
        sb.append(",创建用户=").append(createUser);
        sb.append(",修改用户=").append(updateUser);
        sb.append(",创建时间=").append(createTime);
        sb.append(",修改时间=").append(updateTime);
        return sb.toString();
    }
}
