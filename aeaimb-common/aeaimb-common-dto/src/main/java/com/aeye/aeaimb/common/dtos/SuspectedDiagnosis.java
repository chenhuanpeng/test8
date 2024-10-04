package com.aeye.aeaimb.common.dtos;

import lombok.Data;

/**
 * 疑似病历
 */
@Data
public class SuspectedDiagnosis extends TagDiagnosis{

	/**
     * 相似程度
	 * @mock 0.98
	 */
	protected Double similarity;
	/**
     * 病历ID
	 * @mock xxxxxxxxx
	 */
	protected String medicalId;

	/**
	 * 用于响应给前端使用
	 */
	protected String kgType;

	public SuspectedDiagnosis() {
	}

	public SuspectedDiagnosis(String code, String name, String desc, Double similarity, String medicalId) {
		super(code, name, desc);

		this.similarity = similarity;
		this.medicalId = medicalId;
	}
}
