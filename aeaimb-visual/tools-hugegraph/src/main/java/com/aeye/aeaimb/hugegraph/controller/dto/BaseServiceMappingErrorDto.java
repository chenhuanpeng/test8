package com.aeye.aeaimb.hugegraph.controller.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医院服务项目映射(BaseServiceMapping)实体类
 *
 * @author linkeke
 * @since 2024-07-22 17:16:05
 */
@Data
public class BaseServiceMappingErrorDto implements Serializable {
    private static final long serialVersionUID = 692078234018819423L;
	/**
	 * 服务项目编码
	 */
	@ExcelProperty(value = "项目编码")
	private String serviceCode;
	/**
	 * 服务项目名称
	 */
	@ExcelProperty(value = "项目名称")
	private String serviceName;
	/**
	 * 项目类别
	 */
	@ExcelProperty(value = "项目类别")
	private String serviceType;
	/**
	 * 院方项目编码
	 */
	@ExcelProperty(value = "院方项目代码")
	private String orgServiceCode;
	/**
	 * 院方项目名称
	 */
	@ExcelProperty(value = "院方项目名称")
	private String orgServiceName;
	/**
	 * 错误信息
	 */
	@ExcelProperty(value = "错误信息")
	private String errorMsg;

}

