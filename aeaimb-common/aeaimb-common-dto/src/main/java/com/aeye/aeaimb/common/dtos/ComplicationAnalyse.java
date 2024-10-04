package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.List;

/**
 * 危重症/并发症/危急值
 */
@Data
public class ComplicationAnalyse {
	/**
	 * 病种编码
	 * @mock 323232
	 */
	private String code;
	/**
	 * 病种名称
	 * @mock 心血管并发症
	 */
	private String name;
	/**
	 * 归因分析(简短)
	 * @mock 症状、检验和检查结果表明可能存在心肌缺血和左心室肥大的迹象，这需要及时的干预和管理，以降低进一步发展心血管并发症的风险。
	 */
	private String desc;

	/**
	 * 子类型：testing、examine
	 */
	private String subType;
	/**
	 * 检验检查类型
	 */
	private String cate;
	/**
	 * 危急值
	 */
	private String crisisValue;

	/**
	 * 危急值
	 */
	private List<String> direIllness;
	/**
	 * 是否有详情展示
	 */
	private boolean detailFlag;
	/**
	 * 推荐归因
	 */
	private List<ReasonContent> texts;
}
