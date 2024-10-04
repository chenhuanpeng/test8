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
public class CheckTestDictKgMappingDto implements Serializable {
	private static final long serialVersionUID = -49378283130147871L;

	/**
	 * 项目类型
	 */
	@ExcelProperty(value = "项目类型")
	private String type;

	/**
	 * 检查检验编码
	 */
	@ExcelProperty(value = "检查检验编码")
	private String serviceCode;
	/**
	 * 检查检验名称
	 */
	@ExcelProperty(value = "检查检验名称")
	private String serviceName;
	/**
	 * 知识库检查检验代码
	 */
	@ExcelProperty(value = "知识库检查检验代码")
	private String id;

	/**
	 * 知识库检查检验名称
	 */
	@ExcelProperty(value = "知识库检查检验名称")
	private String name;
	/**
	 * 知识来源
	 */
	@ExcelProperty(value = "知识来源")
	private String resource;

}

