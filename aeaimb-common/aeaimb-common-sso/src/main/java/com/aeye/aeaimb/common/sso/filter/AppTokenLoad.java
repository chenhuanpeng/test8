package com.aeye.aeaimb.common.sso.filter;

import com.aeye.aeaimb.common.sso.filter.dto.PortalUser;

public interface AppTokenLoad {

	/**
	 * 接入端获取后台 token
	 * @param portalUser
	 * @return
	 */
	String loadAppToken(PortalUser portalUser);
}
