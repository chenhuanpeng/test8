package com.aeye.aeaimb.common.dtos;

import lombok.Data;

/**
 * 量表
 */
@Data
public class RecommendScale {
	/**
	 * 量表编码
	 * @mock 1111
	 */
	private String code;
	/**
	 * 量表名称
	 * @mock 卒中量表(NIHSS)
	 */
	private String name;
	/**
	 * 归因
	 * @mock 用于评估卒中的严重程度，包括11个项目，如意识程度、回答问题的能力、遵从指令的能力、眼球运动等。
	 */
	private String desc;
}
