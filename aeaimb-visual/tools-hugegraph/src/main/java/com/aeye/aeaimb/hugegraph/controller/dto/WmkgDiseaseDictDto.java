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
public class WmkgDiseaseDictDto implements Serializable {
	private static final long serialVersionUID = -49378283130147871L;

	/**
	 * 疾病编码
	 */
	@ExcelProperty(value = "诊断代码")
	private String diseaseCode;
	/**
	 * 疾病名称
	 */
	@ExcelProperty(value = "诊断名称")
	private String diseaseName;
	/**
	 * 分类编码规范
	 */
	@ExcelProperty(value = "疾病分类版本")
	private String normType;

	/**
	 * 医保编码
	 */
	@ExcelProperty(value = "诊断代码（医保）")
	private String insurCode;
	/**
	 * 医保名称
	 */
	@ExcelProperty(value = "诊断名称（医保）")
	private String insurName;
	/**
	 * 医保编码分类
	 */
	@ExcelProperty(value = "医保分类版本")
	private String insurNormType;


	/**
	 * 类目编码
	 */
	@ExcelProperty(value = "章")
	private String cateCode1;
	/**
	 * 类目编码
	 */
	@ExcelProperty(value = "章代码范围")
	private String cateRange1;
	/**
	 * 类目名称
	 */
	@ExcelProperty(value = "章的名称")
	private String cateName1;

	/**
	 * 类目编码
	 */
	@ExcelProperty(value = "节代码范围")
	private String cateRange2;
	/**
	 * 类目名称
	 */
	@ExcelProperty(value = "节名称")
	private String cateName2;

	@ExcelProperty(value = "类目代码")
	private String cateCode3;
	/**
	 * 类目名称
	 */
	@ExcelProperty(value = "类目名称")
	private String cateName3;

	@ExcelProperty(value = "亚目代码")
	private String cateCode4;
	/**
	 * 类目名称
	 */
	@ExcelProperty(value = "亚目名称")
	private String cateName4;

}

