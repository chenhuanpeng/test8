package com.aeye.aeaimb.common.webui.configs;

import com.aeye.aeaimb.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 异常信息收集处理
 */
@RestControllerAdvice
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 3)
public class ExceptionResponseBodyAdvice extends AbstractResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        boolean supports = super.supports(returnType, converterType);
        if(!supports)return supports;

        Executable executable = returnType.getExecutable();
        Class<?> declaringClass = executable.getDeclaringClass();
        RestControllerAdvice controllerAdvice = declaringClass.getAnnotation(RestControllerAdvice.class);
        ExceptionHandler exceptionHandler = executable.getAnnotation(ExceptionHandler.class);
        if(declaringClass == BasicErrorController.class){
            return true;
        }
        if(controllerAdvice != null && exceptionHandler != null){
            return true;
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Executable executable = returnType.getExecutable();
        Method method = returnType.getMethod();

        AnnotatedType annotatedReturnType = executable.getAnnotatedReturnType();
        Type type = annotatedReturnType.getType();
        Class<?> declaringClass = executable.getDeclaringClass();
        RestControllerAdvice controllerAdvice = declaringClass.getAnnotation(RestControllerAdvice.class);

        if(declaringClass == BasicErrorController.class){
        	if (body instanceof HashMap){
				/**
				 * {
				 *       "timestamp": "2023-10-26 09:52:21",
				 *       "status": 500,
				 *       "error": "Internal Server Error",
				 *       "path": "/cdss/recommend"
				 *     },
				 */
				return R.err("-1").data(body);
			}
            // 如果已经被 BasicErrorController 拦截，则只需要取其中的 message 信息做返回
            ResponseEntity responseEntity = (ResponseEntity) body;
            HttpStatus statusCode = responseEntity.getStatusCode();
            Map<String,Object> map = (Map<String, Object>) responseEntity.getBody();
            Object message = map.get("message");
            return R.err(statusCode.value() + "").msg(Objects.toString(message));
        }

        // 经测试，这里的值只能有一个，方法可以返回 void ，表示不处理异常
        ExceptionHandler exceptionHandler = executable.getAnnotation(ExceptionHandler.class);
        if(controllerAdvice != null && exceptionHandler != null){       //当前方法必须保证是 ControllerAdvice 并且是 ExceptionHandler
            Class<? extends Throwable>[] exceptions = exceptionHandler.value();
            Class<? extends Throwable> exception = exceptions[0];
            //如果是进入全局异常处理，则处理全局异常中的返回
            if(type == Void.class){
                //如果返回空，不处理异常,返回前端成功，给出一个警告信息
                log.warn("异常未处理，异常处理方法为:{},异常类为:",method,exception);
                return R.ok();
            }
            if(type == R.class){
                return body;
            }

			if (type instanceof ParameterizedTypeImpl){
				Class<?> rawType = ((ParameterizedTypeImpl) type).getRawType();
				if (rawType == R.class){
					R r = (R) body;
					if (r != null) {
						r.setTraceId(MDC.get(TraceIdFilter.UNIQUE_ID));
					}
					return body;
				}
			}

            // 将会把返回值转 String,请事先实现 toString 接口
            String errorInfo = Objects.toString(body);

            return  R.err(exception.getSimpleName()).msg(errorInfo);
        }

        return body;
    }
}
