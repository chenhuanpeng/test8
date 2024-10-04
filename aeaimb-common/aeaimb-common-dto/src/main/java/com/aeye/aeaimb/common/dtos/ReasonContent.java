package com.aeye.aeaimb.common.dtos;

import lombok.Data;

@Data
public class ReasonContent {
	/**
	 * 标题
	 */
	private String textTitle;
	/**
	 * 内容
	 */
	private String textContent;
	/**
	 * 顺序
	 */
	private Integer sort;
}
