package com.ifacebox.web.common.model;

import com.ifacebox.web.admin.master.model.MasterUser;
import com.ifacebox.web.admin.master.service.MasterUserService;
import com.ifacebox.web.common.utils.UserAgentUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author znn
 */
public class UserAgent {
    private Long date;
    private String ip;
    private String os;
    private String browser;
    private String method;
    private String url;
    private String message;
    private final HttpSession session;

    public UserAgent(HttpServletRequest request) {
        this.session = request.getSession(true);
        this.setMessage(request.getHeader("User-Agent"));
        this.setDate(System.currentTimeMillis());
        this.setIp(UserAgentUtils.getIp(request));
        this.setOs(UserAgentUtils.getOs(message));
        this.setBrowser(UserAgentUtils.getBrowser(message));
        this.setMethod(request.getMethod());
        this.setUrl(request.getRequestURI().replaceAll(request.getContextPath(), ""));
    }

    public HttpSession getSession() {
        return session;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MasterUser getMasterUser() {
        return (MasterUser) session.getAttribute(MasterUserService.ADMIN_SESSION);
    }

    public void setMasterUser(MasterUser masterUser) {
        if (masterUser == null) {
            session.removeAttribute(MasterUserService.ADMIN_SESSION);
        } else {
            session.setAttribute(MasterUserService.ADMIN_SESSION, masterUser);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("时间=").append(date);
        sb.append(",IP地址=").append(ip);
        sb.append(",系统=").append(os);
        sb.append(",浏览器=").append(browser);
        sb.append(",方法=").append(method);
        sb.append(",请求地址=").append(url);
        return sb.toString();
    }
}
