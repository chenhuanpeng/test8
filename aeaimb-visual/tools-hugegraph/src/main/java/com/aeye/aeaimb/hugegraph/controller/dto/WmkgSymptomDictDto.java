package com.aeye.aeaimb.hugegraph.controller.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 症状字典(BaseSymptomDict)实体类
 *
 * @author linkeke
 * @since 2024-07-21 15:39:50
 */
@Data
public class WmkgSymptomDictDto implements Serializable {
	private static final long serialVersionUID = 749135666710176385L;
	/**
	 * 症状编码
	 */
	@ExcelProperty(value = "症状编码")
	private String symptomCode;
	/**
	 * 症状名称
	 */
	@ExcelProperty(value = "症状名称")
	private String symptomName;

}

