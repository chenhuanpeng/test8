package com.aeye.aeaimb.common.sso.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("app-token")
public class AppTokenLoadProperties {
	/**
	 * 实现方式(完整类路径)
	 */
	private String impl;
	/**
	 * http 方式实现
	 */
	private Http http;

	@Data
	public static class Http{
		/**
		 * 请求地址, 一般是网关地址
		 */
		private String url;
		/**
		 * 请求头字段名
		 */
		private String headerName;
		/**
		 * 拼接到 token 前面的内容
		 */
		private String alg;
	}
}
