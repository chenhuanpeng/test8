package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 病历文书合理性检查
 */
@Data
public class MedicalCheck {
	/**
	 * 文书节点名称
	 * @mock 门诊病历
	 */
	private String typeName;
	/**
	 * 合理性
	 */
	private List<Rationality> rationalities = new ArrayList<>();

	public MedicalCheck() {
	}

	public MedicalCheck(String typeName) {
		this.typeName = typeName;
	}
}
