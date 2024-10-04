package com.aeye.aeaimb.common.feign.core;

import com.aeye.aeaimb.common.core.constant.SecurityConstants;
import com.aeye.aeaimb.common.feign.annotation.NoToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;

/**
 * @author lengleng
 * @date 2024/6/1
 */
public class CdssFeignInnerRequestInterceptor implements RequestInterceptor, Ordered {
    /**
     * Called for every request. Add data using methods on the supplied {@link RequestTemplate}.
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        Method method = template.methodMetadata().method();
        NoToken noToken = method.getAnnotation(NoToken.class);
        if (noToken != null) {
            template.header(SecurityConstants.FROM, SecurityConstants.FROM_IN);
        }
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
