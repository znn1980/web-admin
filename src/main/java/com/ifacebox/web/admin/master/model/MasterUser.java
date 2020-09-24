package com.ifacebox.web.admin.master.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author znn
 */
public class MasterUser {
    private Integer id;
    @NotBlank(message = "用户姓名不能为空！")
    @Size(min = 2, max = 32, message = "用户姓名长度应在2-32之间！")
    private String name;
    @NotBlank(message = "用户名称不能为空！")
    @Size(min = 2, max = 32, message = "用户名称长度应在2-32之间！")
    private String username;
    private String password;
    @NotBlank(message = "手机号码不能为空！")
    @Size(min = 8, max = 11, message = "手机号码长度应在8-11之间！")
    private String mobile;
    @NotBlank(message = "邮箱地址不能为空！")
    @Email(message = "邮箱地址不正确！")
    private String email;
    @NotNull(message = "用户属性不能为空！")
    private Integer attrId;
    private String attrExplain;
    @NotNull(message = "用户状态不能为空！")
    private Integer statusId;
    private String statusExplain;
    private String loginIp;
    private String loginTime;
    private String createUser;
    private String updateUser;
    private String createTime;
    private String updateTime;
    private Integer[] groupId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
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

    public Integer[] getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer[] groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("编码=").append(id);
        sb.append(",用户姓名=").append(name);
        sb.append(",用户名称=").append(username);
        sb.append(",手机号码=").append(mobile);
        sb.append(",邮箱地址=").append(email);
        sb.append(",属性=").append(attrId);
        sb.append(",状态=").append(statusId);
        sb.append(",创建用户=").append(createUser);
        sb.append(",修改用户=").append(updateUser);
        sb.append(",创建时间=").append(createTime);
        sb.append(",修改时间=").append(updateTime);
        return sb.toString();
    }
}
