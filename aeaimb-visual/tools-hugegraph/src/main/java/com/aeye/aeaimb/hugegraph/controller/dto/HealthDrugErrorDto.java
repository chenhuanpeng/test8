package com.aeye.aeaimb.hugegraph.controller.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 临床指南表(TcmsClinicalGuide)实体类
 *
 * @author linkeke
 * @since 2024-06-11 16:58:16
 */
@Data
public class HealthDrugErrorDto implements Serializable {
    private static final long serialVersionUID = 995466553525800525L;

    @ExcelProperty(value = "entityId")
    private String entityId;

    @ExcelProperty(value = "entitySourceCode")
    private String entitySourceCode;

	@ExcelProperty(value = "entityType")
	private String entityType;

	@ExcelProperty(value = "property")
	private String property;

	@ExcelProperty(value = "valueId")
	private String valueId;

	@ExcelProperty(value = "valueSourceCode")
	private String valueSourceCode;

	@ExcelProperty(value = "value")
	private String value;

	@ExcelProperty(value = "valueType")
	private String valueType;

	@ExcelProperty(value = "group")
	private String group;

	@ExcelProperty(value = "source")
	private String source;

	/**
	 * 错误信息
	 */
	@ExcelProperty(value = "错误信息")
	private String errorMsg;
}

