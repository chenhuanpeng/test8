package com.aeye.aeaimb.core.service.dtos.aggregation;

import lombok.Data;

/**
 * 患者信息
 */
@Data
public class PatientInfo {
	/**
	 * 病人ID
	 */
	private String patientId;

	/**
	 * 病人名称
	 */
	private String userName;

	/**
	 * 性别 1: 男, 2: 女
	 */
	private Integer gender;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 年龄单位: 岁, 月, 天; YEAR, MONTH, DAY
	 */
	private String ageType;
	/**
	 * 身份证
	 */
	private String idcard;

	/**
	 * 是否怀孕，1/0
	 */
	private String pregnancy;
}
