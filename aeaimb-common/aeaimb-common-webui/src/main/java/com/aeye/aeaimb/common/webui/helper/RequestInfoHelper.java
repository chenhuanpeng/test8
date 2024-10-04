package com.aeye.aeaimb.common.webui.helper;

import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestInfoHelper {

    /**
     * 客户端 ip 信息
     * @return
     */
    public String requestIp() {
        HttpServletRequest request = request();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 浏览器及系统版本信息
     * @return
     */
    public UserAgent userAgent(){
        String userAgent = request().getHeader("User-Agent");
        return UserAgent.parseUserAgentString(userAgent);
    }

    /**
     * 获取 request
     * @return
     */
    public HttpServletRequest request(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
