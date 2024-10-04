package com.aeye.aeaimb.hugegraph.controller.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 图谱标准字典映射(BaseGraphMapping)实体类
 *
 * @author linkeke
 * @since 2024-07-21 15:58:03
 */
@Data
public class WmkgGraphMappingErrorDto implements Serializable {
	private static final long serialVersionUID = 612853583374056390L;

	/**
	 * 图谱名称
	 */
	@ExcelProperty(value = "图谱名称")
	private String graphName;
	/**
	 * 标准编码
	 */
	@ExcelProperty(value = "项目编码")
	private String standCode;
	/**
	 * 标准名称
	 */
	@ExcelProperty(value = "项目名称")
	private String standName;
	/**
	 * 字典分类: 疾病、手术、检查、检验、药品
	 */
	private String mediDictType;

	/**
	 * 错误信息
	 */
	@ExcelProperty(value = "错误信息")
	private String errorMsg;

}

