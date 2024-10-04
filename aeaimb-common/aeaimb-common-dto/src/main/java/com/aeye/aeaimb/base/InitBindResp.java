package com.aeye.aeaimb.base;

import lombok.Data;

/**
 * 初始化绑定响应
 * 医院, 医生, 科室信息
 */
@Data
public class InitBindResp {
	private DeptmentInfo deptmentInfo;
	private DoctorBaseInfo doctorBaseInfo;
	private OrgBaseInfo orgBaseInfo;
}
