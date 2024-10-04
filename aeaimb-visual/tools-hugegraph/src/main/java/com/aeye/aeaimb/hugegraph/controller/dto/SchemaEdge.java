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
public class SchemaEdge implements Serializable {
    private static final long serialVersionUID = 995466553525800525L;
    /**
     * 源顶点
     */
    @ExcelProperty(value = "源顶点")
    private String sourceVertex;
    /**
     * 边关系
     */
    @ExcelProperty(value = "边关系")
    private String link;

	/**
	 * 目标顶点
	 */
	@ExcelProperty(value = "目标顶点")
    private String targetVertex;

	/**
	 * 边属性
	 */
	@ExcelProperty(value = "边属性",index = 3)
	private String property1;

	@ExcelProperty(value = "边属性",index = 4)
	private String property2;

	@ExcelProperty(value = "边属性",index = 5)
	private String property3;

}

