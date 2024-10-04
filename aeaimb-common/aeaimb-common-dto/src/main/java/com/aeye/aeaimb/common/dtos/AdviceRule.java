package com.aeye.aeaimb.common.dtos;

import lombok.Data;

@Data
public class AdviceRule {
	/**
	 * 警戒值
	 */
	private String labTestItemValue;
	/**
	 * 处置建议
	 */
	private String labTestItemDesc;
	/**
	 * 严重级别
	 */
	private String labTestItemLevel;

	/**
	 * 警戒值, 接口弄重复了
	 */
	private String textItemValue;

	/**
	 * 处置建议
	 */
	private String textItemDesc;
	/**
	 * 严重级别
	 */
	private String textItemLevel;
}
