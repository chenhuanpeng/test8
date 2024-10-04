package com.aeye.aeaimb.hugegraph.controller.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医院疾病映射(BaseDiseaseMapping)实体类
 *
 * @author linkeke
 * @since 2024-07-21 12:08:40
 */
@Data
public class BaseDiseaseMappingErrorDto implements Serializable {
	private static final long serialVersionUID = 228685849376480670L;
	/**
	 * 分类编码规范
	 */
	@ExcelProperty(value = "疾病分类版本")
	private String normType;
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
	 * 疾病编码
	 */
	@ExcelProperty(value = "院方诊断代码")
	private String orgDiseaseCode;
	/**
	 * 疾病名称
	 */
	@ExcelProperty(value = "院方诊断名称")
	private String orgDiseaseName;

	/**
	 * 错误信息
	 */
	@ExcelProperty(value = "错误信息")
	private String errorMsg;
}

