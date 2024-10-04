package com.aeye.aeaimb.common.webui.configs;

import com.aeye.aeaimb.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Executable;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class SupportOldProjectAdvice extends AbstractResponseBodyAdvice {
    @Autowired(required = false)
    private ResponseHandler responseHandler;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        boolean supports = super.supports(returnType, converterType);
        if(!supports)return supports;

        Executable executable = returnType.getExecutable();
        Class<?> declaringClass = executable.getDeclaringClass();
        RestControllerAdvice controllerAdvice = declaringClass.getAnnotation(RestControllerAdvice.class);
        ExceptionHandler exceptionHandler = executable.getAnnotation(ExceptionHandler.class);
        if(controllerAdvice != null && exceptionHandler != null){
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if((body instanceof R) && responseHandler != null){
			R responseDto = (R) body;
            String code = responseDto.getCode();
            Object newBody = null;
            if("0".equals(code)){
                newBody = responseHandler.handlerError(responseDto);
            }else{
                newBody = responseHandler.handlerOut(responseDto);
            }

            return newBody;
        }
        return body;
    }
}
