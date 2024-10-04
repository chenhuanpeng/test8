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
public class OperationDictKgMappingDto implements Serializable {
	private static final long serialVersionUID = -49378283130147871L;

	/**
	 * 手术分类版本
	 */
	@ExcelProperty(value = "手术分类版本")
	private String type;

	/**
	 * 手术编码
	 */
	@ExcelProperty(value = "手术编码")
	private String operationCode;
	/**
	 * 手术名称
	 */
	@ExcelProperty(value = "手术名称")
	private String operationName;
	/**
	 * 知识库手术代码
	 */
	@ExcelProperty(value = "知识库手术代码")
	private String id;

	/**
	 * 知识库手术名称
	 */
	@ExcelProperty(value = "知识库手术名称")
	private String name;
	/**
	 * 知识来源
	 */
	@ExcelProperty(value = "知识来源")
	private String resource;

}

