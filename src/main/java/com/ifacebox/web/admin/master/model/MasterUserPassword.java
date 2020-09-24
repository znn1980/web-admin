package com.ifacebox.web.admin.master.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author znn
 */
public class MasterUserPassword {
    private String oldPassword;
    @NotBlank(message = "密码不能为空！")
    @Size(min = 6, max = 64, message = "密码长度应在6-64之间！")
    private String password;
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
