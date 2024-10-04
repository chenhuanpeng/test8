package com.aeye.aeaimb.common.webui.logmark;

import com.aeye.aeaimb.common.webui.configs.StreamParamHandlerAdvice;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Getter
@Slf4j
public class LogInfo {
    private JoinPoint joinPoint;
    private HttpServletRequest request;

    private BaseInfo baseInfo;
    private HeaderInfo headerInfo;
    private Map<String,String> headerUserInfo = new HashMap<>();
    private BodyInfo bodyInfo;

    private Date currentDate;

    public LogInfo(JoinPoint joinPoint,HttpServletRequest request,HeaderInfo headerInfo) {
        this.joinPoint = joinPoint;
        this.request = request;
        this.headerInfo = headerInfo;
        this.currentDate = new Date();

        parserBaseInfo();

        parserHeaderInfo();

        parserBodyInfo();
    }

    final static String [] basicHeaderKey =
            {"access-control-request-headers","access-control-request-method",
                    "origin","referer","user-agent","content-type",
                    "host","connection","content-length","pragma","accept","cache-control","accept-encoding","accept-language"};

    /**
     * 解析除了基本头信息外的其它头信息
     */
    private void parserHeaderInfo() {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headerKey = headerNames.nextElement();
            if(ArrayUtils.contains(basicHeaderKey,headerKey)){continue;}
            headerUserInfo.put(headerKey,request.getHeader(headerKey));
        }
    }

    /**
     * 主体信息解析，包含查询串和请求主体
     */
    private void parserBodyInfo() {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MimeType mimeType = MimeTypeUtils.parseMimeType(request.getContentType());
        String body = StreamParamHandlerAdvice.STREAM_PARAMS_THREAD_LOCAL.get();
        StreamParamHandlerAdvice.STREAM_PARAMS_THREAD_LOCAL.remove();        // 移除当前参数，后面不需要用到了
        this.bodyInfo = new BodyInfo(parameterMap,body);
    }

    /**
     * 基础信息解析
     */
    private void parserBaseInfo() {
        URI methodPath = null;
        try {
            String contextPath = request.getContextPath();
            methodPath = new URI(contextPath).relativize(new URI(request.getRequestURI()));
        } catch (URISyntaxException e) {
            log.error("解析请求地址出错",e);
            try {
                methodPath = new URI(request.getRequestURI());
            } catch (URISyntaxException ex) {}
        }
        String method = request.getMethod();
        this.baseInfo = new BaseInfo(method, methodPath.toString());
    }

    /**
     * 主体信息，包含查询串和请求体
     */
    @Getter
    static class BodyInfo{
        private Map<String,String[]> parameterMap;
        private String body;

        public BodyInfo(Map<String, String[]> parameterMap, String body) {
            this.parameterMap = parameterMap;
            this.body = body;
        }
    }

    @Getter
    static class HeaderInfo{
        private String contentType;
        private String ip;
        private UserAgent userAgent;

        public HeaderInfo(String contentType, String ip, UserAgent userAgent) {
            this.contentType = contentType;
            this.ip = ip;
            this.userAgent = userAgent;
        }
    }

    @Getter
    static class BaseInfo{
        private String method;
        private String path;

        public BaseInfo(String method, String path) {
            this.method = method;
            this.path = path;
        }
    }
}
