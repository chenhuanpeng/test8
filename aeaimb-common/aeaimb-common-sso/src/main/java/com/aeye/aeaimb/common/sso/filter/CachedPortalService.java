package com.aeye.aeaimb.common.sso.filter;

import com.aeye.aeaimb.common.sso.filter.dto.PortalUser;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CachedPortalService {

	private PortalService portalService;

	@Autowired
	public CachedPortalService(PortalService portalService) {
		this.portalService = portalService;
	}

	/**
	 * 用户信息缓存  10 分钟过期, 最大 10000 个用户
	 */
	Cache<String, PortalUser> userInfoCache = Caffeine.newBuilder()
			.expireAfterWrite(10, TimeUnit.MINUTES)
			.maximumSize(10_000)
			.build();

	/**
	 * 缓存方式获取用户信息
	 * @param ssoToken
	 * @return
	 */
	public PortalUser getUserName(String ssoToken){
		return userInfoCache.get(ssoToken, k -> portalService.getUserName(ssoToken));
	}
}
