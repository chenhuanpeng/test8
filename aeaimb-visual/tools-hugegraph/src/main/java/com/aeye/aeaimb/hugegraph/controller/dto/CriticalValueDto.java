package com.aeye.aeaimb.hugegraph.controller.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 临床指南表(TcmsClinicalGuide)实体类
 *
 * @author linkeke
 * @since 2024-06-11 16:58:16
 */
@Data
public class CriticalValueDto implements Serializable {
    private static final long serialVersionUID = 995466553525800525L;
	/**
	 * 项目名称
	 */
	@ExcelProperty(value = "项目")
	private String projectName;
	/**
	 * 项目类型:检验、检查
	 */
	@ExcelProperty(value = "类型（检查、检验）")
	private String projectType;
	/**
	 * 指标
	 */
	@ExcelProperty(value = "指标")
	private String indicatorName;
	/**
	 * 指标值
	 */
	@ExcelProperty(value = "指标值")
	private String indicatorValue;
	/**
	 * 指标单位
	 */
	@ExcelProperty(value = "指标单位")
	private String indicatorUnit;
	/**
	 * 适用年龄（天）
	 */
	@ExcelProperty(value = "适用年龄（天）")
	private String suitAge;
	/**
	 * 适用科室
	 */
	@ExcelProperty(value = "适用科室")
	private String suitDept;
	/**
	 * 危急描述
	 */
	@ExcelProperty(value = "危急描述")
	private String crisisDesc;

}

