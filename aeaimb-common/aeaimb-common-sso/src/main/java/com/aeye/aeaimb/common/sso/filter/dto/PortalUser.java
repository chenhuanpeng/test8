package com.aeye.aeaimb.common.sso.filter.dto;

import lombok.Data;

@Data
public class PortalUser {

	private BasicInfo sysUser;

	@Data
	public static class BasicInfo {
		private String username;
	}

}
