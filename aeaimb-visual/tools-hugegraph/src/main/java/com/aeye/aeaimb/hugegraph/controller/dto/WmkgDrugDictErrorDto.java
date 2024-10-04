package com.aeye.aeaimb.hugegraph.controller.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 药品字典(BaseDrugDict)实体类
 *
 * @author linkeke
 * @since 2024-07-21 15:58:02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDrugDictErrorDto implements Serializable {
	private static final long serialVersionUID = -98424898963346174L;

	/**
	 * 药品编码
	 */
	@ExcelProperty(value = "药品代码")
	private String drugCode;
	/**
	 * 药品名称
	 */
	@ExcelProperty(value = "药品通用名")
	private String drugName;

	/**
	 * 分类编码
	 */
	@ExcelProperty(value = "一级分类编码")
	private String cateCode1;
	/**
	 * 分类名称
	 */
	@ExcelProperty(value = "一级分类名称")
	private String cateName1;

	/**
	 * 分类编码
	 */
	@ExcelProperty(value = "二级分类编码")
	private String cateCode2;
	/**
	 * 分类名称
	 */
	@ExcelProperty(value = "二级分类名称")
	private String cateName2;

	/**
	 * 分类编码
	 */
	@ExcelProperty(value = "三级分类编码")
	private String cateCode3;
	/**
	 * 分类名称
	 */
	@ExcelProperty(value = "三级分类名称")
	private String cateName3;

	/**
	 * 分类编码
	 */
	@ExcelProperty(value = "四级分类编码")
	private String cateCode4;
	/**
	 * 分类名称
	 */
	@ExcelProperty(value = "四级分类名称")
	private String cateName4;

	/**
	 * 错误信息
	 */
	@ExcelProperty(value = "错误信息")
	private String errorMsg;

}

