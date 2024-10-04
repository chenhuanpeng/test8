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
public class ReasonFactorDto implements Serializable {
    private static final long serialVersionUID = 995466553525800525L;
	/**
	 * 因子分类编码
	 */
	@ExcelProperty(value = "因子分类编码")
	private String factorType;
	/**
	 * 因子分类名称
	 */
	@ExcelProperty(value = "因子分类名称")
	private String factorName;
	/**
	 * 因子分类排序
	 */
	@ExcelProperty(value = "因子分类排序")
	private Integer factorSort;
	/**
	 * 因子父分类
	 */
	@ExcelProperty(value = "父分类名称")
	private String parentFactor;
	/**
	 * 因子层级
	 */
	@ExcelProperty(value = "层级")
	private Integer factorLvl;
	/**
	 * 值性质:定性、定量
	 */
	@ExcelProperty(value = "因子值性质")
	private String factorDefine;
	/**
	 * 因子数据类型：多个值、单个值
	 */
	@ExcelProperty(value = "因子数据类型")
	private String factorDataType;
	/**
	 * 因子数据单位
	 */
	@ExcelProperty(value = "因子数据单位")
	private String factorUnit;
}

