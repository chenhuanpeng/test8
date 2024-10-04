package com.aeye.aeaimb.hugegraph.controller.dto.kg;

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
public class DrugDictKgMappingDto implements Serializable {
	private static final long serialVersionUID = -49378283130147871L;

	/**
	 * 药品代码
	 */
	@ExcelProperty(value = "药品代码")
	private String drugCode;
	/**
	 * 通用名称
	 */
	@ExcelProperty(value = "通用名称")
	private String drugName;
	/**
	 * 知识库疾病代码
	 */
	@ExcelProperty(value = "知识库药品代码")
	private String id;

	/**
	 * 知识库疾病名称
	 */
	@ExcelProperty(value = "知识库药品名称")
	private String name;
	/**
	 * 知识来源
	 */
	@ExcelProperty(value = "知识来源")
	private String resource;

}

