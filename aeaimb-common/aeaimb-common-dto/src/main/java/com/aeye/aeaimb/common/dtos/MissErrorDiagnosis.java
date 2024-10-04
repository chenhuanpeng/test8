package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.List;

/**
 * 漏诊误诊实体
 */
@Data
public class MissErrorDiagnosis extends TagDiagnosis{
	/**
	 * 源诊断编码
	 */
	protected String sourceCode;

	/**
	 * 源诊断名称
	 */
	protected String sourceName;

	/**
	 * 诊断类型 miss漏诊，error误诊
	 */
	protected String diagnosticType;
	/**
	 * 疾病是否有详情
	 */
	protected boolean detailFlag;
	/**
	 * 漏诊/误诊/鉴别诊断, 都是疾病类型的 kgType
	 */
	protected String kgType = "T1";
	/**
	 * 推荐归因
	 */
	protected List<ReasonContent> texts;

	public MissErrorDiagnosis() {
	}

	public MissErrorDiagnosis(String code, String name, String desc) {
		this.code = code;
		this.name = name;
		this.desc = desc;
	}
}
