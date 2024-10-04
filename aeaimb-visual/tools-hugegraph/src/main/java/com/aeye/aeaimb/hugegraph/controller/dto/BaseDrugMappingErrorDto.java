package com.aeye.aeaimb.hugegraph.controller.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医院药品映射(BaseDrugMapping)实体类
 *
 * @author linkeke
 * @since 2024-07-22 18:07:37
 */
@Data
public class BaseDrugMappingErrorDto implements Serializable {
	private static final long serialVersionUID = -19318004902839251L;

	/**
	 * 药品编码
	 */
	@ExcelProperty(value = "药品代码")
	private String drugCode;
	/**
	 * 药品名称
	 */
	@ExcelProperty(value = "药品通用名")
	private String drugName;
	/**
	 * 院方药品编码
	 */
	@ExcelProperty(value = "院方药品代码")
	private String orgDrugCode;
	/**
	 * 院方药品名称
	 */
	@ExcelProperty(value = "院方药品名称")
	private String orgDrugName;
	/**
	 * 药品企业
	 */
	@ExcelProperty(value = "厂家")
	private String orgDrugCompany;
	/**
	 * 规格
	 */
	@ExcelProperty(value = "规格")
	private String orgDrugSpec;
	/**
	 * 错误信息
	 */
	@ExcelProperty(value = "错误信息")
	private String errorMsg;

}

