package com.aeye.aeaimb.common.webui.logmark;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 日志处理默认实现，直接打印部分信息
 */
@Slf4j
@Component
public class Slf4jLogInfoHandler implements LogInfoHandler {
    @Value("${sanri.webui.logmark.showInfos:base,header,param,body}")
    private String [] showInfos;

    @Override
    public void before(LogInfo logInfo) {}

    @Override
    public void around(AroundLogInfo aroundLogInfo) {
        LogInfo logInfo = aroundLogInfo.getLogInfo();
        LogInfo.BaseInfo baseInfo = logInfo.getBaseInfo();
        LogInfo.BodyInfo bodyInfo = logInfo.getBodyInfo();
        LogInfo.HeaderInfo headerInfo = logInfo.getHeaderInfo();
        Duration duration = aroundLogInfo.getDuration();

        if(ArrayUtils.contains(showInfos,"base")){
            log.info(baseInfo.getMethod()+" /"+ baseInfo.getPath()+" 执行时间:"+duration.toMillis()+" ms");
        }
        if(ArrayUtils.contains(showInfos,"header")){
            log.info(baseInfo.getMethod()+" /"+ baseInfo.getPath()+" 请求头:"+logInfo.getHeaderUserInfo());
        }
        if(ArrayUtils.contains(showInfos,"param") && ArrayUtils.contains(showInfos,"body")) {
            log.info(baseInfo.getMethod() + " /" + baseInfo.getPath() + " 查询参数:" + bodyInfo.getParameterMap() + ",主体参数:" + bodyInfo.getBody());
        }else{
            if(ArrayUtils.contains(showInfos,"param")){
                log.info(baseInfo.getMethod() + " /" + baseInfo.getPath() + " 查询参数:" + bodyInfo.getParameterMap());
            }
            if(ArrayUtils.contains(showInfos,"body")){
                log.info(baseInfo.getMethod() + " /" + baseInfo.getPath() + " 主体参数:" + bodyInfo.getBody());
            }
        }
    }
}
