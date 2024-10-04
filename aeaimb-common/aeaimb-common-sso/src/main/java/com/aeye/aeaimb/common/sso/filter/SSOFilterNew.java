package com.aeye.aeaimb.common.sso.filter;

import cn.hutool.extra.spring.SpringUtil;
import com.aeye.aeaimb.common.core.constant.SecurityConstants;
import com.aeye.aeaimb.common.sso.configuration.AppTokenLoadProperties;
import com.aeye.aeaimb.common.sso.filter.dto.PortalUser;
import com.aeye.aeaimb.mkpb.feign.RemoteMkpbTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class SSOFilterNew implements Filter {
	CachedPortalService cachedPortalService = SpringUtil.getBean(CachedPortalService.class);

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		RemoteMkpbTokenService remoteMkpbTokenService = SpringUtil.getBean(RemoteMkpbTokenService.class);

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String ssoToken = request.getHeader(SecurityConstants.SSO_TOKEN);

		if (StringUtils.isBlank(ssoToken)) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		try {
			PortalUser portalUser = cachedPortalService.getUserName(ssoToken);
			AppTokenLoadProperties appTokenLoadProperties = SpringUtil.getBean(AppTokenLoadProperties.class);
			AppTokenLoad appTokenLoad = SpringUtil.getBean(appTokenLoadProperties.getImpl());
			String appToken = appTokenLoad.loadAppToken(portalUser);

			// 创建一个包装类来修改请求头里面的Authorization
			HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request) {
				@Override
				public String getHeader(String name) {
					if (HttpHeaders.AUTHORIZATION.equals(name)) {
						return appToken;
					}
					return super.getHeader(name);
				}
			};

			//继续过滤链，携带接口后端token走接入后端(比如知识中台)的后续权限认证流程
			filterChain.doFilter(wrappedRequest, servletResponse);
		} catch (Exception e) {
			((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			return;
		}
	}

}
