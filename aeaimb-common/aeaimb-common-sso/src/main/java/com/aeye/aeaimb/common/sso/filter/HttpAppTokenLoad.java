package com.aeye.aeaimb.common.sso.filter;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.aeye.aeaimb.common.core.exception.BusinessException;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.sso.configuration.AppTokenLoadProperties;
import com.aeye.aeaimb.common.sso.filter.dto.PortalUser;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

@Slf4j
public class HttpAppTokenLoad implements AppTokenLoad{

	@Autowired
	private AppTokenLoadProperties appTokenLoadProperties;

	@Override
	public String loadAppToken(PortalUser portalUser) {
		AppTokenLoadProperties.Http http = appTokenLoadProperties.getHttp();
		if (http == null){
			throw BusinessException.create("使用 http 方式配置接入端 token 获取, 没有配置");
		}
		HttpRequest httpRequest = HttpUtil.createGet(http.getUrl());
		HttpResponse execute = httpRequest.execute();
		if (!execute.isOk()){
			throw BusinessException.create("获取接入端 token 失败");
		}
		R r = JSON.parseObject(execute.body(), R.class);

		return (String) r.getData();
	}
}
