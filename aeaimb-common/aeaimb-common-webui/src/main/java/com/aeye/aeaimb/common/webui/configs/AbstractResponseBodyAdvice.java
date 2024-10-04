package com.aeye.aeaimb.common.webui.configs;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Executable;
import java.util.List;

@Slf4j
public abstract class AbstractResponseBodyAdvice implements ResponseBodyAdvice {
    /**
     * 对于像 swaggerui 等，排除部分包的处理，使用它本身的映射
     * 这里写正则表达式
     */
    @Value("${webui.advice.ignore.packages:}")
    protected List<String> adviceIgnorePackages;

    public boolean supports(MethodParameter returnType, Class converterType) {
        Executable executable = returnType.getExecutable();
        Class<?> declaringClass = executable.getDeclaringClass();
        String packageName = ClassUtils.getPackageName(declaringClass);

        //添加常用技术的过滤支持，如 swagger-ui
        if(packageName.startsWith("springfox.documentation.swagger")){
            return false;
        }

        if(CollectionUtils.isEmpty(adviceIgnorePackages)){
            return true;
        }
        if (adviceIgnorePackages.contains(packageName)){
        	log.debug("忽略包: {}", packageName);
        	return false;
		}
        return true;
    }
}
