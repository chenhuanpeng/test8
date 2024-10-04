package com.aeye.aeaimb.common.sso.filter;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.aeye.aeaimb.common.core.constant.SecurityConstants;
import com.aeye.aeaimb.common.core.exception.BusinessException;
import com.aeye.aeaimb.common.sso.filter.dto.PortalUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import static com.aeye.aeaimb.common.core.constant.SecurityConstants.BEARER;

/**
 * 门户平台数据服务
 */
@Slf4j
public class PortalService {

	/**
	 * 门户平台获取用户信息数据
	 */
	@Value("portal.url.userInfo:http://192.168.16.133:9999/admin/user/info")
	private String loadUserInfoUrl;

	/**
	 * 获取用户名
	 * @param ssoToken
	 * @return
	 */
	public PortalUser getUserName(String ssoToken){
		String authorization = SecurityConstants.BEARER + ssoToken;

		HttpRequest upmsReq = HttpUtil.createGet(loadUserInfoUrl)
				.header(HttpHeaders.AUTHORIZATION, authorization);
		HttpResponse execute = upmsReq.execute();
		if (!execute.isOk()){
			log.error("门户平台验证用户信息接口异常: {}", execute.body());
			throw BusinessException.create("门户平台验证用户信息接口异常");
		}

		JSONObject jsonObject = JSON.parseObject(execute.body());
		String code = jsonObject.getString("code");
		if (!"0".equals(code)){
			log.error("门户平台验证用户信息接口异常: {}", jsonObject.getString("msg"));
			throw BusinessException.create("门户平台验证用户信息接口异常, "+ jsonObject.getString("msg"));
		}

		PortalUser portalUser = jsonObject.toJavaObject(PortalUser.class);
		return portalUser;
	}
}
