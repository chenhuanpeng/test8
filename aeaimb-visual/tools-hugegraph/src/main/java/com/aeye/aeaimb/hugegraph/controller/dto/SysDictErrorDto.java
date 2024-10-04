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
public class SysDictErrorDto implements Serializable {
    private static final long serialVersionUID = 995466553525800525L;
	/**
	 * 类型编码
	 */
	@ExcelProperty(value = "类型编码")
	private String dictType;
	/**
	 * 类型名称
	 */
	@ExcelProperty(value = "类型名称")
	private String description;

	/**
	 * 错误信息
	 */
	@ExcelProperty(value = "错误信息")
	private String errorMsg;
}

