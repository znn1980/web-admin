package com.ifacebox.web.common.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * @author znn
 */
public class UserAgentUtils {
    public static final String UNKNOWN = "unknown";
    public static final String IP_SPLIT = ",";
    public static final int IP_MAX_LENGTH = 15;

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isEmpty(ip) && ip.length() > IP_MAX_LENGTH) {
            if (ip.indexOf(IP_SPLIT) > 0) {
                ip = ip.substring(0, ip.indexOf(IP_SPLIT));
            }
        }
        return ip;
    }

    public static String getOs(String userAgent) {
        OperatingSystem operatingSystem = OperatingSystem.parseUserAgentString(userAgent);
        return operatingSystem.getName();
    }

    public static String getBrowser(String userAgent) {
        Browser browser = Browser.parseUserAgentString(userAgent);
        return browser.getName();
    }
}
