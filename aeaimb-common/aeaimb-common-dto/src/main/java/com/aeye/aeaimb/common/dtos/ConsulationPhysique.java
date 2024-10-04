package com.aeye.aeaimb.common.dtos;

import lombok.Data;

@Data
public class ConsulationPhysique {
	/**
	 * 问题id
	 * @mock 001
	 */
	private String questionId;
	/**
	 * 问题标题
	 * @mock 眼部检查
	 */
	private String title;
	/**
	 * 检查描述
	 * @mock 检查眼睑：检查眼睑的开合情况、颜色、形态等，以评估眼部肌肉和神经的功能状况
	 */
	private String descr;
}
