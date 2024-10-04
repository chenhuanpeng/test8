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

package com.aeye.aeaimb.common.security.service;

import com.aeye.cdss.admin.api.dto.UserDTO;
import com.aeye.cdss.admin.api.dto.UserInfo;
import com.aeye.cdss.admin.api.feign.RemoteUserService;
import com.aeye.aeaimb.common.core.constant.CacheConstants;
import com.aeye.aeaimb.common.core.constant.SecurityConstants;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.core.util.WebUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详细信息
 *
 * @author lengleng hccake
 */
@Slf4j
@Primary
@RequiredArgsConstructor
public class CdssUserDetailsServiceImpl implements CdssUserDetailsService {

	private final RemoteUserService remoteUserService;

	private final CacheManager cacheManager;

	/**
	 * 用户名密码登录
	 * @param username 用户名
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		String orgId = WebUtils.getRequest().get().getHeader("Orgid");
		String key = "";
		String[] split = username.split("\\^");
		//超级管理员
		if (split.length>0 && StringUtils.equals("admin", split[0])){
			username = split[0];
			key = username;
		}

		//没有传orgId，并且不是admin
		if (split.length == 1 && !StringUtils.equals("admin", split[0])){
			username = split[0];
			key = username;
		}

		//有传orgId，并且不是admin
        if (split.length > 1 && !StringUtils.equals("admin", split[0])){
			username = split[0];
			orgId = split[1];
			key = username +":" +orgId;
		}

        if (cache != null && cache.get(key) != null) {
            return (CdssUser) cache.get(key).get();
		}

		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(username);
		userDTO.setOrgId(orgId);
		R<UserInfo> result = remoteUserService.info(userDTO, SecurityConstants.FROM_IN);
		UserDetails userDetails = getUserDetails(result);
		if (cache != null) {
			cache.put(key, userDetails);
		}
		return userDetails;
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
