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
public class CriticalValueErrorDto implements Serializable {
    private static final long serialVersionUID = 995466553525800525L;

    @ExcelProperty(value = "项目")
    private String project;

    @ExcelProperty(value = "指标")
    private String metrics;

	@ExcelProperty(value = "指标值")
	private String metricsValue;

	@ExcelProperty(value = "指标单位")
	private String metricsUnit;

	@ExcelProperty(value = "适用年龄（天）")
	private String age;

	@ExcelProperty(value = "适用科室")
	private String dept;

	@ExcelProperty(value = "危急描述")
	private String description;

	/**
	 * 错误信息
	 */
	@ExcelProperty(value = "错误信息")
	private String errorMsg;
}

