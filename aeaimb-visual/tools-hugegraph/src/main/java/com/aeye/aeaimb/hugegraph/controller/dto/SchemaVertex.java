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
public class SchemaVertex implements Serializable {
    private static final long serialVersionUID = 995466553525800525L;

	@ExcelProperty(value = "分类")
	private String group;

    /**
     * 实体
     */
    @ExcelProperty(value = "实体")
    private String vertex;
    /**
     * 属性
     */
    @ExcelProperty(value = "属性",index = 2)
    private String property1;

	@ExcelProperty(value = "属性",index = 3)
	private String property2;

	@ExcelProperty(value = "属性",index = 4)
	private String property3;

	@ExcelProperty(value = "属性",index = 5)
	private String property4;

	@ExcelProperty(value = "属性",index = 6)
	private String property5;

	@ExcelProperty(value = "属性",index = 7)
	private String property6;

	@ExcelProperty(value = "属性",index = 8)
	private String property7;

	@ExcelProperty(value = "属性",index = 9)
	private String property8;

	@ExcelProperty(value = "属性",index = 10)
	private String property9;
}

