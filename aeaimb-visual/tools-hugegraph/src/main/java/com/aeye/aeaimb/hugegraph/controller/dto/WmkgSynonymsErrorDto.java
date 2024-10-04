package com.aeye.aeaimb.hugegraph.controller.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * [西医]同义词(WmkgSynonyms)实体类
 *
 * @author linkeke
 * @since 2024-07-17 13:33:23
 */
@Data
public class WmkgSynonymsErrorDto implements Serializable {
	private static final long serialVersionUID = -46362620649159633L;
	/**
	 * 同义词编码
	 */
	@ExcelProperty(value = "类型编码")
	private String synonymsCode;
	/**
	 * 对象编码
	 */
	@ExcelProperty(value = "术语编码")
	private String objCode;
	/**
	 * 对象分类
	 */
	@ExcelProperty(value = "类型")
	private String objType;
	/**
	 * 源名称
	 */
	@ExcelProperty(value = "术语名称")
	private String sourceName;
	/**
	 * 目标名称
	 */
	@ExcelProperty(value = "同义词")
	private String targetName;

	/**
	 * 错误信息
	 */
	@ExcelProperty(value = "错误信息")
	private String errorMsg;

}

