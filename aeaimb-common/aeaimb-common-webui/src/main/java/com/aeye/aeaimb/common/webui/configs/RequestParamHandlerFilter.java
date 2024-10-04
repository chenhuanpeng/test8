package com.aeye.aeaimb.common.webui.configs;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/**
 * 参数处理器,这里处理 request.getParamMap 中的数据
 */
@Component
@Order(1)
@WebFilter(urlPatterns = "/**", filterName = "RequestParamHandlerFilter", dispatcherTypes = DispatcherType.REQUEST)
public class RequestParamHandlerFilter implements Filter {
    private Function<String,String> paramHandler;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        TrimRequestWrapper trimRequestWrapper = new TrimRequestWrapper(httpServletRequest);
        filterChain.doFilter(trimRequestWrapper,servletResponse);
    }

    @Override
    public void destroy() {}

    /**
     * 去除 x-www-urlformEncoded 中的空格和特殊字符
     */
    public class TrimRequestWrapper extends HttpServletRequestWrapper {
        private Map<String, String[]> params = new HashMap<String, String[]>();//保存处理后的参数

        public TrimRequestWrapper(HttpServletRequest request) {
            super(request);
            this.params.putAll(request.getParameterMap());
            this.modifyParameterValues();
        }

        private void modifyParameterValues() {
            Set<Map.Entry<String, String[]>> entrys = params.entrySet();
            for (Map.Entry<String, String[]> entry : entrys) {
                String[] values = entry.getValue();
                if (values != null && values.length > 0){
					for (int i = 0; i < values.length; i++) {
						values[i] = StringUtils.trim(values[i]);
						if(paramHandler != null) {
							values[i] = paramHandler.apply(StringUtils.trim(values[i]));
						}
					}
				}

                this.params.put(entry.getKey(), values);
            }
        }

        @Override
        public Enumeration<String> getParameterNames() {//重写getParameterNames()
            return new Vector<String>(params.keySet()).elements();
        }

        @Override
        public String getParameter(String name) {
            String[] values = params.get(name);
            if (values == null || values.length == 0) {
                return null;
            }
            return values[0];
        }

        @Override
        public String[] getParameterValues(String name) {
            return params.get(name);
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return this.params;
        }
    }

    @Autowired(required = false)
    public void setParamHandler(Function<String, String> paramHandler) {
        this.paramHandler = paramHandler;
    }
}
