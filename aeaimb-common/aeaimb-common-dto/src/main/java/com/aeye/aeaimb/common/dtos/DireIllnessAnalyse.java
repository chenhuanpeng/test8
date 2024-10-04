package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.List;

/**
 * 弃用, 危急值合并到危重症中
 */
@Data
@Deprecated
public class DireIllnessAnalyse {
	/**
	 *检查检验编码
	 * @mock 1111
	 */
	private String code;
	/**
	 * 检查检验名称
	 * @mock 血红蛋白
	 */
	private String name;
	/**
	 * 描述
	 * @mock 患者血红蛋白 20g/L，低于危急值低值 45g/L，请及时联系临床医生。
	 */
	private String desc;
	/**
	 * 检查/检验类型
	 * 1: 检验 , 2 : 检查
	 * @mock 1
	 */
	private String type;
	/**
	 * 正常：N 偏高：H 偏低：L
	 * @mock N
	 */
	private String change;
	/**
	 * 处置建议
	 */
	private List<AdviceRule> texts;


}
