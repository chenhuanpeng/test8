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
public class SymptomDictKgMappingDto implements Serializable {
	private static final long serialVersionUID = -49378283130147871L;

	/**
	 * 症状编码
	 */
	@ExcelProperty(value = "症状编码")
	private String symptomCode;
	/**
	 * 症状名称
	 */
	@ExcelProperty(value = "症状名称")
	private String symptomName;
	/**
	 * 知识库症状代码
	 */
	@ExcelProperty(value = "知识库症状代码")
	private String id;

	/**
	 * 知识库症状名称
	 */
	@ExcelProperty(value = "知识库症状名称")
	private String name;
	/**
	 * 知识来源
	 */
	@ExcelProperty(value = "知识来源")
	private String resource;

}

