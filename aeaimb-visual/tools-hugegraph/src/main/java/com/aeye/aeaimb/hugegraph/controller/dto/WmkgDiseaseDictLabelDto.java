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
public class WmkgDiseaseDictLabelDto implements Serializable {
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
	 * 科室名称及编码
	 */
	@ExcelProperty(value = "科室名称及编码")
	private String deptName;
	/**
	 * 系统名称及编码
	 */
	@ExcelProperty(value = "系统名称及编码")
	private String systemName;
	/**
	 * 标签名称及编码
	 */
	@ExcelProperty(value = "标签名称及编码")
	private String label;
}

