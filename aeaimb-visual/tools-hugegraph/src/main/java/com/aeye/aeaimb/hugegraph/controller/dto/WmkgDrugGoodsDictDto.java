package com.aeye.aeaimb.hugegraph.controller.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 药品字典(BaseDrugDict)实体类
 *
 * @author linkeke
 * @since 2024-07-21 15:58:02
 */
@Data
public class WmkgDrugGoodsDictDto implements Serializable {
	private static final long serialVersionUID = -98424898963346174L;

	/**
	 * 药品编码
	 */
	@ExcelProperty(value = "药品代码")
	private String drugCode;
	/**
	 * 药品名称
	 */
	@ExcelProperty(value = "注册名称")
	private String drugName;

	/**
	 * 药品本位码
	 */
	@ExcelProperty(value = "药品本位码")
	private String regCode;

	/**
	 * 批准文号
	 */
	@ExcelProperty(value = "批准文号")
	private String approvalNumber;

	/**
	 * 剂型
	 */
	@ExcelProperty(value = "剂型")
	private String dosageForm;
	/**
	 * 规格
	 */
	@ExcelProperty(value = "规格")
	private String specifications;

	/**
	 * 商品名称
	 */
	@ExcelProperty(value = "商品名称")
	private String drugNameGe;
	/**
	 * 包装材质
	 */
	@ExcelProperty(value = "包装材料")
	private String packagingMaterial;

	/**
	 * 最小包装数量
	 */
	@ExcelProperty(value = "最小包装数量")
	private String packagingQuantity;
	/**
	 * 最小制剂单位
	 */
	@ExcelProperty(value = "最小制剂单位")
	private String formulationUnit;

	/**
	 * 最小包装单位
	 */
	@ExcelProperty(value = "最小包装单位")
	private String packagingUnit;
	/**
	 * 药品企业
	 */
	@ExcelProperty(value = "药品企业")
	private String manufactor;

}

