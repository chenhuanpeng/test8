package com.aeye.aeaimb.common.dtos;

import lombok.Data;

/**
 * 检查/检验细项
 */
@Data
public class LabTestReportItem {
	/**
	 * 细项编码
	 * @mock 0001
	 */
	private String code;
	/**
	 * 细项名称
	 * @mock 血小板
	 */
	private String name;
	/**
	 * 细项描述
	 * @mock 其结果用于辅助诊断高血压
	 */
	private String desc;
}
