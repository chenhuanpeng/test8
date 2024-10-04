package com.aeye.aeaimb.common.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 症候相似度
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SyndromeSimilarity extends SyndromeExtend{
	/**
	 * 相似度
	 * @mock 0.98
	 */
	private double similarity;
	/**
	 * 是否选中
	 */
	private boolean checked;
}
