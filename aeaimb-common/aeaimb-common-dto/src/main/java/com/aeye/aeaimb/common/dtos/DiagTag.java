package com.aeye.aeaimb.common.dtos;

import lombok.Data;

/**
 * 疾病标签
 */
@Data
public class DiagTag {
	/**
     * 标签简称
	 */
	private String alias;
	/**
     * 标签级别
	 */
	private String level;
	/**
	 * 提示语
	 */
	private String tips;

	public DiagTag() {
	}

	public DiagTag(String alias, String level) {
		this.alias = alias;
		this.level = level;
	}
}
