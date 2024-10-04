package com.aeye.aeaimb.common.dtos;

import lombok.Data;

/**
 * 中医疾病
 */
@Data
public class Disease {
	/**
	 * 疾病编码
	 * @mock 1111
	 */
	private String diseaseCode;
	/**
	 * 疾病名称
	 * @mock 高血压
	 */
	private String diseaseName;

	/**
	 * 科室编码(中医科室)
	 */
	private String deptCode;

	public Disease() {
	}

	public Disease(String diseaseCode, String diseaseName) {
		this.diseaseCode = diseaseCode;
		this.diseaseName = diseaseName;
	}
}
