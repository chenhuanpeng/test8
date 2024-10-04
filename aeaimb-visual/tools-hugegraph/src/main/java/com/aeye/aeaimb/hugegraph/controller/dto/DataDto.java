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
public class DataDto implements Serializable {
    private static final long serialVersionUID = 995466553525800525L;

    @ExcelProperty(value = "疾病属性")
    private String property;

    @ExcelProperty(value = "属性值")
    private String propertyValue;

	@ExcelProperty(value = "属性值1")
	private String propertyValue1;

	@ExcelProperty(value = "属性值2")
	private String propertyValue2;

	@ExcelProperty(value = "属性值3")
	private String propertyValue3;

	@ExcelProperty(value = "属性值4")
	private String propertyValue4;

	@ExcelProperty(value = "属性值5")
	private String propertyValue5;


}

