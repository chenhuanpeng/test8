package com.aeye.aeaimb.common.sso.filter;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.aeye.aeaimb.common.core.constant.SecurityConstants;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.feign.RemoteMkpbTokenService;
import com.aeye.cdss.admin.api.dto.UserInfo;
import com.aeye.cdss.admin.api.entity.SysUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class SsoFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 初始化代码

		log.info("初始化");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		RemoteMkpbTokenService remoteMkpbTokenService = SpringUtil.getBean(RemoteMkpbTokenService.class);

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		//1、URL请求，检查请求头，获取Sso-Token
		String ssoToken = request.getHeader(SecurityConstants.SSO_TOKEN);
		if (Objects.nonNull(ssoToken) ) {

			StringBuilder auth  = new StringBuilder();
			auth.append(SecurityConstants.BEARER);
			auth.append(ssoToken);

			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
			if(null == inputStream){
				log.error("资源文件config.properties未找到");
				((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			}else{
				Properties properties = new Properties();
				properties.load(inputStream);
				String upmsUserInfoUrl = properties.getProperty("upms.userinfo.url");

				//2、门户平台Sso-Token验证，获取门户平台账号基础信息
				HttpRequest upmsReq = HttpUtil.createGet(upmsUserInfoUrl)
						.header(HttpHeaders.AUTHORIZATION, auth.toString());
				HttpResponse execute = upmsReq.execute();
				if (execute.isOk()) {
					String body = execute.body();
					R<UserInfo> rUserInfo = JSON.parseObject(body, new TypeReference<R<UserInfo>>() {
					});

					UserInfo data = rUserInfo.getData();
					SysUser sysUser = data.getSysUser();

					//获取接入后端(知识中台)的token
					R<String> ztToken = remoteMkpbTokenService.getZtToken(sysUser.getUsername());
					if (!ztToken.isSuccess()){
						//返回401
						((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
					}
					String mkpbToken =  ztToken.getData();

					// 创建一个包装类来修改请求头里面的Authorization
					HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request) {
						@Override
						public String getHeader(String name) {
							if (HttpHeaders.AUTHORIZATION.equals(name)) {
								return SecurityConstants.BEARER + mkpbToken;
							}
							return super.getHeader(name);
						}
					};
					//继续过滤链，携带接口后端token走接入后端(比如知识中台)的后续权限认证流程
					filterChain.doFilter(wrappedRequest, servletResponse);
				}else{
					//返回401
					((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				}
			}

		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void destroy() {

		//filter 测试
		log.info("销毁");
		// 销毁代码
	}
}
