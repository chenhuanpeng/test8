package com.aeye.aeaimb.common.dtos;

import lombok.Data;

/**
 * 诊断
 */
@Data
public class Diagnosis {
	/**
     * 诊断病编码
	 * @mock m0098
	 */
	protected String code;
	/**
     * 诊断病名称
	 * @mock 高血压
	 */
	protected String name;
	/**
	 * 诊断描述
	 * @mock 高血压
	 */
	protected String desc;

	/**
	 * 主诊断标志, 当索引号为 0 时为主诊断
	 * @mock 0
	 */
	protected Integer index;

	/**
	 * 诊断类型 2、中医诊断、3、中医证候
	 * @mock 3
	 */
	protected String diagnosisType;

	public Diagnosis() {
	}

	public Diagnosis(String code, String name, String desc) {
		this.code = code;
		this.name = name;
		this.desc = desc;
	}
}
