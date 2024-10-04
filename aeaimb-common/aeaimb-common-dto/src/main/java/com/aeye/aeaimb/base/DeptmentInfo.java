package com.aeye.aeaimb.base;

import lombok.Data;

@Data
public class DeptmentInfo {
	/**
     * [内部]科室编码
	 */
	private String deptId;
	/**
     * 科室名称
	 */
	private String deptName;
	/**
     * [医院]科室编码
	 */
	private String deptGuid;

	/**
	 * 标准科室编码
	 */
	private String standardDeptCode;

	public DeptmentInfo() {
	}

	public DeptmentInfo(String deptId, String deptName) {
		this.deptId = deptId;
		this.deptName = deptName;
	}

	public DeptmentInfo(String deptId, String deptName, String deptGuid) {
		this.deptId = deptId;
		this.deptName = deptName;
		this.deptGuid = deptGuid;
	}
}
