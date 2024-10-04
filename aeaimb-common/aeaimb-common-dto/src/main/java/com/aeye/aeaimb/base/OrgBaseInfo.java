package com.aeye.aeaimb.base;

import lombok.Data;

@Data
public class OrgBaseInfo {
	/**
     * [内部]机构编码
	 */
	private String orgId;
	/**
     * 机构名称/医院名称
	 */
	private String orgName;
	/**
     * [外部]机构编码
	 */
	private String hospitalGuid;
}
