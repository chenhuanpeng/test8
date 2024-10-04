package com.aeye.aeaimb.hugegraph.controller.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 疾病字典(BaseDiseaseDict)实体类
 *
 * @author linkeke
 * @since 2024-07-21 12:08:39
 */
@Data
public class WmkgOperateDictErrorDto implements Serializable {
	private static final long serialVersionUID = -49378283130147871L;

	/**
	 * 手术编码
	 */
	@ExcelProperty(value = "手术操作代码")
	private String operationCode;
	/**
	 * 手术名称
	 */
	@ExcelProperty(value = "手术操作名称")
	private String operationName;
	/**
	 * 分类编码规范
	 */
	@ExcelProperty(value = "手术分类版本")
	private String normType;

	/**
	 * 医保编码
	 */
	@ExcelProperty(value = "手术操作代码（医保）")
	private String insurCode;
	/**
	 * 医保名称
	 */
	@ExcelProperty(value = "手术操作名称（医保）")
	private String insurName;
	/**
	 * 医保编码分类
	 */
	@ExcelProperty(value = "医保分类版本")
	private String insurNormType;


	@ExcelProperty(value = "章")
	private String cateCode1;

	@ExcelProperty(value = "章的名称")
	private String cateName1;

	@ExcelProperty(value = "类目代码")
	private String cateCode2;

	@ExcelProperty(value = "类目名称")
	private String cateName2;

	@ExcelProperty(value = "亚目代码")
	private String cateCode3;
	@ExcelProperty(value = "亚目名称")
	private String cateName3;

	@ExcelProperty(value = "细目代码")
	private String cateCode4;
	@ExcelProperty(value = "细目名称")
	private String cateName4;

	/**
	 * 错误信息
	 */
	@ExcelProperty(value = "错误信息")
	private String errorMsg;

}

