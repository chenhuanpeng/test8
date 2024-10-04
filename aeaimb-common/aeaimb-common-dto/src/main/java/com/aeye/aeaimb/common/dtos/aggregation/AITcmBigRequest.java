package com.aeye.aeaimb.common.dtos.aggregation;

import com.aeye.aeaimb.common.dtos.*;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public final class AITcmBigRequest {
	/**
	 * 公共参数
	 */
	private PublicParam publicParam;
	/**
	 * 病历
	 */
	private Medical medical;
	/**
	 * 问答信息
	 */
	private QAInfo qaInfo;

	/**
	 * 药品处方开立
	 */
	private List<Medication> medications = new ArrayList<>();
	/**
	 * 操作治疗信息
	 */
	private List<Manitherapy> operatorSchemas = new ArrayList<>();

	/**
	 * CDSS 疾病 + 症候信息
	 */
	private List<TcmbDiagInfo> diagnoses;

	/**
	 * 症状列表
	 */
	@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "classname")
	private List<? extends Symptom> symptoms;


}
