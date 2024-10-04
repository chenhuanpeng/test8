package com.aeye.aeaimb.common.webui.logmark;

import com.aeye.aeaimb.common.webui.helper.RequestInfoHelper;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;

@Slf4j
@Aspect
@Component
public class SysLogMarkAspect implements ApplicationContextAware {
    @Autowired
    private RequestInfoHelper requestInfoHelper;
    @Autowired
    private LogInfoHandler logInfoHandler;
    private ApplicationContext applicationContext;

    @Pointcut("@annotation(com.aeye.aeaimb.common.webui.logmark.SysLogMark)")
    public void pointcut() {}

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        LogInfo logInfo = logInfoParser(joinPoint);

        logInfoHandler.before(logInfo);
    }



    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LogInfo logInfo = logInfoParser(proceedingJoinPoint);
        StopWatch stopWatch = new StopWatch();stopWatch.start();
        Object proceed = null;
        try {
            proceed  = proceedingJoinPoint.proceed();
        }finally {
            //执行失败也需要日志信息
            stopWatch.stop();
            AroundLogInfo aroundLogInfo = new AroundLogInfo(logInfo, Duration.ofMillis(stopWatch.getTime()), proceed);

            logInfoHandler.around(aroundLogInfo);
        }

        return proceed;
    }

    /**
     * 日志信息解析
     * @param joinPoint
     * @return
     */
    public LogInfo logInfoParser(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 请求头信息
        String requestIp = requestInfoHelper.requestIp();
        UserAgent userAgent = requestInfoHelper.userAgent();
        String contentType = request.getContentType();
        LogInfo.HeaderInfo headerInfo = new LogInfo.HeaderInfo(contentType, requestIp, userAgent);

       return new LogInfo(joinPoint, request, headerInfo);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
