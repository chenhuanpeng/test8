package com.aeye.aeaimb.common.sso.filter;

import com.aeye.aeaimb.common.sso.filter.dto.PortalUser;
import lombok.extern.slf4j.Slf4j;

/**
 * 基于永久常量的 token 获取
 */
@Slf4j
public class ConstantAppTokenLoad implements AppTokenLoad{
	@Override
	public String loadAppToken(PortalUser portalUser) {
		return "admin";
	}
}
