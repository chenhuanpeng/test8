package com.aeye.aeaimb.hugegraph.controller.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医院手术映射(BaseOperationMapping)实体类
 *
 * @author linkeke
 * @since 2024-07-21 15:15:37
 */
@Data
public class BaseOperationMappingDto implements Serializable {
	private static final long serialVersionUID = -32941809236729929L;
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
	 * 院方手术编码
	 */
	@ExcelProperty(value = "院方手术操作代码")
	private String orgOperationCode;
	/**
	 * 院方手术名称
	 */
	@ExcelProperty(value = "院方手术操作名称")
	private String orgOperationName;

}

