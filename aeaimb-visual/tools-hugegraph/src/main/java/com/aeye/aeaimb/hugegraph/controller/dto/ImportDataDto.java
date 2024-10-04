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
public class ImportDataDto implements Serializable {
    private static final long serialVersionUID = 995466553525800525L;

    @ExcelProperty(value = "疾病")
    private String diagName;

    @ExcelProperty(value = "手术")
    private String surgery;

	@ExcelProperty(value = "操作治疗")
	private String operator;

	@ExcelProperty(value = "检验")
	private String testing;

	@ExcelProperty(value = "检查")
	private String check;

	@ExcelProperty(value = "药品")
	private String drugs;

	@ExcelProperty(value = "症状")
	private String symptom;

}

