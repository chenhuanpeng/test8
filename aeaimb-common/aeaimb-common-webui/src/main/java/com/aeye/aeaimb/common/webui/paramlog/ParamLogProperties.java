package com.aeye.aeaimb.common.webui.paramlog;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("paramlog")
@Data
public class ParamLogProperties {
	/**
	 * 是否启用日志打印
	 */
	private boolean enable;
	/**
	 * 是否打印接口耗时
	 */
	private boolean enableCostTime;

	/**
	 * 单条日志允许打印最大长度
	 */
	private int singleLogMaxLength = 1000;
	/**
	 * 不打印参数日志的接口, 同时也不会打印消耗时间
	 */
	private List<String> ignoreUrls;
}
