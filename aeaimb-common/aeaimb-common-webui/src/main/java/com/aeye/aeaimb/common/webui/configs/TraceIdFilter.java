package com.aeye.aeaimb.common.webui.configs;

import com.aeye.aeaimb.common.webui.helper.SnowflakeIdWorker;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TraceIdFilter implements Filter {
    public static final String UNIQUE_ID = "traceId";

	SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(1,1);

	@Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		String uniqueId = String.valueOf(snowflakeIdWorker.nextId());
        MDC.put(UNIQUE_ID, uniqueId);
        try {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.addHeader(UNIQUE_ID,uniqueId);
            filterChain.doFilter(servletRequest, servletResponse);
        }finally {
            MDC.remove(UNIQUE_ID);
        }
    }
}
