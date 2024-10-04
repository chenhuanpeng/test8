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
public class SysDictItemDto implements Serializable {
    private static final long serialVersionUID = 995466553525800525L;
	/**
	 * 类型编码
	 */
	@ExcelProperty(value = "类型编码")
	private String dictType;

	@ExcelProperty(value = "字典编码")
	private String itemValue;

	@ExcelProperty(value = "字典名称")
	private String label;

	@ExcelProperty(value = "描述")
	private String description;

}

