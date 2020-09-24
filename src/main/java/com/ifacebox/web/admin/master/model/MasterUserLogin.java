package com.ifacebox.web.admin.master.model;

import javax.validation.constraints.NotBlank;

/**
 * @author znn
 */
public class MasterUserLogin {
    @NotBlank(message = "用户名称不能为空！")
    private String username;
    @NotBlank(message = "用户密码不能为空！")
    private String password;
    @NotBlank(message = "验证码不能为空！")
    private String captcha;

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

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
