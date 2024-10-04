/*
 * Copyright (c) 2020 cdss4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aeye.aeaimb.common.feign;

import com.aeye.aeaimb.common.feign.core.CdssFeignInnerRequestInterceptor;
import com.aeye.aeaimb.common.feign.sentinel.ext.CdssSentinelFeign;
import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aeye.aeaimb.common.feign.sentinel.handle.CdssUrlBlockHandler;
import com.aeye.aeaimb.common.feign.sentinel.parser.CdssHeaderRequestOriginParser;
import feign.Feign;
import feign.RequestInterceptor;
import okhttp3.*;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.TimeUnit;

/**
 * sentinel 配置
 *
 * @author lengleng
 * @date 2020-02-12
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class CdssFeignAutoConfiguration {

	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "feign.sentinel.enabled")
	public Feign.Builder feignSentinelBuilder() {
		return CdssSentinelFeign.builder();
	}

	@Bean
	@ConditionalOnMissingBean
	public BlockExceptionHandler blockExceptionHandler(ObjectMapper objectMapper) {
		return new CdssUrlBlockHandler(objectMapper);
	}

	@Bean
	@ConditionalOnMissingBean
	public RequestOriginParser requestOriginParser() {
		return new CdssHeaderRequestOriginParser();
	}

	/**
	 * 连接池配置
	 *
	 * @return 连接池
	 */
	@Bean
	public ConnectionPool pool() {
		// 最大连接数、连接存活时间、存活时间单位（分钟）
		return new ConnectionPool(200, 5, TimeUnit.MINUTES);
	}

	/**
	 * OkHttp 客户端配置
	 * @return OkHttp 客户端配
	 */
	@Bean
	public okhttp3.OkHttpClient okHttpClient() {
		return new okhttp3.OkHttpClient.Builder()
				.retryOnConnectionFailure(false) // 是否开启缓存
			.connectTimeout(10L, TimeUnit.SECONDS) // 连接超时时间
			.readTimeout(60L, TimeUnit.SECONDS) // 读取超时时间
			.writeTimeout(60L, TimeUnit.SECONDS)
			.connectionPool(pool())
			.followRedirects(true) // 是否允许重定向
			.addNetworkInterceptor(chain -> {
				okhttp3.Request build = chain.request().newBuilder().addHeader("Connection", "close").build();
				return chain.proceed(build);
			})
			.build();
	}

	/**
	 * cdss feign 内部请求拦截器
	 *
	 * @return {@link RequestInterceptor }
	 */
	@Bean
	public RequestInterceptor pigFeignInnerRequestInterceptor() {
		return new CdssFeignInnerRequestInterceptor();
	}
}
