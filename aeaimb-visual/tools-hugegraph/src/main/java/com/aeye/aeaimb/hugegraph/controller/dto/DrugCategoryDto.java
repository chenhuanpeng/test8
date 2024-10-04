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
public class DrugCategoryDto implements Serializable {
    private static final long serialVersionUID = 995466553525800525L;

    @ExcelProperty(value = "一级分类编码（2位）")
    private String code1;

    @ExcelProperty(value = "一级分类名称")
    private String code1Name;

	@ExcelProperty(value = "二级分类编码（4位）")
	private String code2;

	@ExcelProperty(value = "二级分类名称")
	private String code2Name;

	@ExcelProperty(value = "三级分类编码（5位）")
	private String code3;

	@ExcelProperty(value = "三级分类名称")
	private String code3Name;

	@ExcelProperty(value = "四级分类编码（6位）")
	private String code4;

	@ExcelProperty(value = "四级分类名称")
	private String code4Name;

}

