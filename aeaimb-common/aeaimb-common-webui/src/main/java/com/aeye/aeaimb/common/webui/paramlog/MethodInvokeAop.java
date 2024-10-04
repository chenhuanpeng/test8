package com.aeye.aeaimb.common.webui.paramlog;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

@Aspect
@Slf4j
@ConditionalOnProperty(value = "paramlog.enable",havingValue = "true")
public class MethodInvokeAop {

	@Autowired
	private ParamLogProperties paramLogProperties;

	@Autowired
	private AntPathMatcher antPathMatcher;

    @Pointcut("execution(public * com.aeye.aeaimb..controller.*.*(..))")
    public void pointcut(){}

	@Pointcut("@annotation(com.aeye.aeaimb.common.webui.paramlog.ParamLog)")
	public void annotationLog(){}

    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

//    @Before("pointcut() || annotationLog()")
    public void before(JoinPoint joinpoint){
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();
		if (isIgnoreUrl(requestURI)){
			log.debug("忽略参数日志: {}", requestURI);
			return ;
		}

        String queryString = request.getQueryString();
        if (queryString == null){
            queryString = "";
        }else{
            queryString = "?"+queryString;
        }

        StringBuffer argsFormat = formatArgs(request, joinpoint.getArgs(), (MethodSignature) joinpoint.getSignature());

		log.info("{} {}{} {}", requestMethod, requestURI, queryString, argsFormat);

    }

    @Around("pointcut() || annotationLog()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();

		if (isIgnoreUrl(requestURI)){
			return proceedingJoinPoint.proceed();
		}

		String queryString = request.getQueryString();
		if (queryString == null){
			queryString = "";
		}else{
			queryString = "?"+queryString;
		}

		StringBuffer argsFormat = formatArgs(request, proceedingJoinPoint.getArgs(), (MethodSignature) proceedingJoinPoint.getSignature());

		Object proceed = null;
        try {
			proceed = proceedingJoinPoint.proceed();
			return proceed;
		}catch (Exception e){
			log.error("{} 接口异常: {}",requestURI, e.getMessage());
			throw e;
		}finally {
			log.info("{} {}{} \n请求:{} \n响应:{}", requestMethod, requestURI, queryString, argsFormat, proceed != null ? JSON.toJSONString(proceed): null);

			if (paramLogProperties.isEnableCostTime()) {
				log.info("{} {} 执行耗时 {} ms", requestMethod, requestURI, (System.currentTimeMillis() - startTime));
			}
        }
    }

	boolean isIgnoreUrl(String url){
		if (CollectionUtils.isEmpty(paramLogProperties.getIgnoreUrls())){
			return false;
		}
		for (String ignoreUrl : paramLogProperties.getIgnoreUrls()) {
			boolean match = antPathMatcher.match(ignoreUrl, url);
			if (match){
				return true;
			}
		}
		return false;
	}

    /**
     * 格式化参数信息
     * @param args
     * @param signature
     * @return
     */
    private StringBuffer formatArgs(HttpServletRequest request, Object[] args, MethodSignature signature) {
        Method method = signature.getMethod();
		String requestMethod = request.getMethod();
		String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);

        StringBuffer builder = new StringBuffer();
		if (ArrayUtils.isNotEmpty(args) && parameterNames != null) {
			for (int i = 0; i < args.length; i++) {
				Object arg = args[i];
				String parameterName = parameterNames[i];
				builder.append(",");

				// 空参处理
				if (arg == null) {
					builder.append(parameterName).append(":");
					continue;
				}

				// 特殊参数处理
				boolean speacialParam = (arg instanceof HttpServletRequest)
						|| (arg instanceof HttpServletResponse)
						|| (arg instanceof HttpSession)
						|| (arg instanceof MultipartFile)
						|| (arg instanceof MultipartFile[])
						|| (arg instanceof ServletRequest)
						|| (arg instanceof ServletResponse);
				if (speacialParam) {
					log.debug("{} 是特殊参数,不打印参数信息", parameterName);
					continue;
				}

				int maxLength = paramLogProperties.getSingleLogMaxLength();

				// base64 参数
				boolean isBase64 = (arg instanceof String) && ((String) arg).length() > maxLength;
				if (isBase64) {
					builder.append(parameterName).append(":").append(((String) arg).substring(0, maxLength)).append("...(May be BASE64,long string)");
					continue;
				}

				// 原型型 ,字符串型,日期 ,BigDecimal 处理
				if (ClassUtils.isPrimitiveOrWrapper(arg.getClass()) || (arg instanceof String) || (arg instanceof Date) || (arg instanceof BigDecimal)) {
					builder.append(parameterName).append(":").append(arg);
					continue;
				}

				// 对象型参数处理
				builder.append(parameterName).append(":").append(JSON.toJSONString(arg));
			}
			if (args.length > 0){
				String substring = builder.substring(1);
				return new StringBuffer(substring);
			}
		}
        return builder;
    }
}
