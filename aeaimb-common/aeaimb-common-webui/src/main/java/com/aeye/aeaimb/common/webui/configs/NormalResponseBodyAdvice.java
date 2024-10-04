package com.aeye.aeaimb.common.webui.configs;


import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.webui.dto.TreeResponseDto;
import com.aeye.aeaimb.common.webui.helper.TreeResponseDtoHelper;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Executable;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * 正常数据收集处理
 */
@RestControllerAdvice
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 2)
public class NormalResponseBodyAdvice extends AbstractResponseBodyAdvice {

	@Value("${wrapper.ignore.urls:}")
	private List<String> ignoreUrls;

	PathMatcher pathMatcher = new AntPathMatcher();

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

        if (ResponseEntity.class.isAssignableFrom(returnType.getMethod().getReturnType())){
        	return false;
		}

		if (returnType.getDeclaringClass().isAnnotationPresent(IgnoreWrapper.class)) {
			log.debug("忽略外层包裹, 直接回写数据给前端");
			return false;
		}
		if (returnType.getMethod().isAnnotationPresent(IgnoreWrapper.class)) {
			log.debug("忽略外层包裹, 直接回写数据给前端");
			return false;
		}

        return true;
    }

    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Executable executable = returnType.getExecutable();
        AnnotatedType annotatedReturnType = executable.getAnnotatedReturnType();
        Type type = annotatedReturnType.getType();

		IgnoreWrapper ignoreWrapper = executable.getAnnotation(IgnoreWrapper.class);
		if (ignoreWrapper != null){
			log.debug("忽略外层包裹, 直接回写数据给前端");
			return body;
		}

		if (!CollectionUtils.isEmpty(ignoreUrls)) {
			URI uri = request.getURI();
			String path = request.getURI().getPath();
			for (String ignoreUrl : ignoreUrls) {
				boolean match = pathMatcher.match(ignoreUrl, path);
				if (match){
					log.debug("忽略外层包裹, 直接回写数据给前端");
					return body;
				}
			}
		}

		// 如果空，则为成功
        if(type == Void.TYPE){
            // 空返回直接返回成功
            return  R.ok();
        }
        //如果本身返回 ResponseDto 则不处理
        if(type == R.class){
        	R r = (R) body;
        	r.setTraceId(MDC.get(TraceIdFilter.UNIQUE_ID));
            return body;
        }

		if (body instanceof String) {
			//解决返回值为字符串时，不能正常包装
			HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
			servletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			return JSON.toJSONString(R.ok(body));
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

        //检查是否需要树状结构返回
        TreeResponse treeResponse = executable.getAnnotation(TreeResponse.class);
        if(treeResponse != null){
            //检查原始返回类型是否为 List 类型，否则给出异常
            if((type instanceof ParameterizedTypeImpl) && ((ParameterizedTypeImpl) type).getRawType() == List.class) {
                List<TreeResponseDto> modifyList = new ArrayList();
                Class<? extends R> treeReposeDtoClass = treeResponse.type();
                String rootId = treeResponse.rootId();
                String rootParentId = treeResponse.rootParentId();

                // 转换结构成可支持树结构
                List results = (List) body;
                for (Object result : results) {
                    TreeResponseDto newInstance = (TreeResponseDto) ReflectUtils.newInstance(treeReposeDtoClass, new Class<?>[]{result.getClass()}, new Object[]{result});
                    modifyList.add(newInstance);
                }

                //转换成树结构，根据需要转成森林或树结构
                if (StringUtils.isNotBlank(rootId)) {
                    body = TreeResponseDtoHelper.fastConvertTree(modifyList, rootId);
                } else if (StringUtils.isNotBlank(rootParentId)) {
                    body = TreeResponseDtoHelper.fastConvertForest(modifyList, rootParentId);
                }

                return R.ok().data(body);
            }
            throw new IllegalArgumentException("树类型返回要求原始数据为 List 类型");
        }

        return R.ok().data(body);
    }
}
