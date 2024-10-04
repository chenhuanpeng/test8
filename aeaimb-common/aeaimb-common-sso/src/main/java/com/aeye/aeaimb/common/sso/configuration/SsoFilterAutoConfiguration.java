package com.aeye.aeaimb.common.sso.configuration;

import com.aeye.aeaimb.common.sso.filter.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(AppTokenLoadProperties.class)
public class SsoFilterAutoConfiguration {

	@Bean
	public FilterRegistrationBean<SsoFilter> highPriorityFilter() {
		FilterRegistrationBean<SsoFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new SsoFilter());
		registrationBean.addUrlPatterns("/*"); // 应用到所有URL
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // 设置最高优先级
		return registrationBean;
	}

//	@Bean
//	public FilterRegistrationBean<SSOFilterNew> highPriorityFilterNew() {
//		FilterRegistrationBean<SSOFilterNew> registrationBean = new FilterRegistrationBean<>();
//		registrationBean.setFilter(new SSOFilterNew());
//		registrationBean.addUrlPatterns("/*"); // 应用到所有URL
//		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // 设置最高优先级
//		return registrationBean;
//	}

	@Bean
	public PortalService portalService(){
		return new PortalService();
	}

	@Bean
	public CachedPortalService cachedPortalService(PortalService portalService){
		return new CachedPortalService(portalService);
	}

	@Bean
	public HttpAppTokenLoad httpAppTokenLoad(){
		return new HttpAppTokenLoad();
	}
}