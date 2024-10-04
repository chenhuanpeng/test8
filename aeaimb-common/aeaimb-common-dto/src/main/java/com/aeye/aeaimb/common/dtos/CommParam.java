package com.aeye.aeaimb.common.dtos;

import lombok.Data;

/**
 * 门诊公共参数
 */
@Data
public class CommParam extends HisCommParam{

	/**
	 * 性别 1男,2女
	 * @mock 1
	 */
	private Integer gender;
	/**
	 * 出生日期 格式： yyyy-MM-dd 或者 yyyyMMdd
	 * @mock 2023-01-01
	 */
	private String birthDate;
	/**
	 * 年龄
	 * @mock 20
	 */
	private Integer age;
	/**
	 * 年龄类型 YEAR, DAY, MONTH
	 * @mock YEAR
	 */
	private String ageType;
	/**
	 * 是否怀孕，1/0
	 */
	private String pregnancy;

	/**
	 * 是否使用 ai 推荐
	 */
	private String aiRecomm;
	/**
	 * 客户端数据来源
	 * cdss 从 cdss 来的数据
	 * his 或为空, his 数据来源
	 */
	private String clientFrom;

}
