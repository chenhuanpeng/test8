package com.aeye.aeaimb.common.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PublicParam {
	/**
     * 就诊流水号_序号
	 * @mock 238767989_0001
	 */
	private String traceId;
	/**
	 * 页面源
	 * @mock 30
	 */
	private Integer pageSourceValue;
	/**
	 * 用户名称
	 * @mock 葫芦娃
	 */
	private String userName;
	/**
	 * 性别 1: 男, 2: 女
	 * @mock 1
	 */
	private Integer gender;
	/**
	 * 年龄
	 * @mock 26
	 */
	private Integer age;
	/**
	 * 年龄单位: 岁, 月, 天 {@link AgeType}
	 * YEAR, MONTH, DAY
	 * @mock YEAR
	 */
	private String ageType;
	/**
     * 科室名称
	 * @mock 内科
	 */
	private String inpatientDepartment;
	/**
     * 科室ID
	 * @mock 77889900
	 */
	private String inpatientDepartmentId;

	/**
	 * 备用字段
	 * @mock remark
	 */
	private String remark;

	/**
	 * 就诊序列号
	 * @mock p00000000001
	 */
	private String serialNumber;

	/**
	 * 是否怀孕，1/0
	 */
	private String pregnancy;

	/**
	 * 是否 AI 推荐
	 */
	private Boolean aiRecomm;

	/**
	 * 年龄类型
	 */
	public enum AgeType{
		YEAR, MONTH,WEEK, DAY
	}
}
