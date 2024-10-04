package com.aeye.aeaimb.core.service.dtos.aggregation;

import com.aeye.aeaimb.common.dtos.PublicParam;
import lombok.Data;

@Data
public class MedicalCompreRequest {
	/**
	 * 对比基准
	 */
	private MedicalPerson origin;
	/**
	 * 对比基准
	 */
	private MedicalPerson target;

	/**
	 * 主要为了存储一个 traceId
	 */
	private PublicParam publicParam;

	public MedicalCompreRequest() {
	}

	public MedicalCompreRequest(MedicalPerson origin, MedicalPerson target) {
		this.origin = origin;
		this.target = target;
	}
}
