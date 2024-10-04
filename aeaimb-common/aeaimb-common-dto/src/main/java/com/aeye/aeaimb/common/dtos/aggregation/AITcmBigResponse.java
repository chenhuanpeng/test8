package com.aeye.aeaimb.common.dtos.aggregation;

import com.aeye.aeaimb.common.dtos.*;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AITcmBigResponse {

	/**
	 * 症状推荐列表
	 */
	private List<Symptom> symptoms;


	/**
	 * 疾病推荐列表
	 */
	@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "c")
	private List<? extends TcmbDiagInfo> tcmbDiagInfos;

	/**
	 * 病历文书合理性
	 */
	private List<MedicalCheck> medicalChecks = new ArrayList<>();

	/**
	 * 四诊之问诊
	 */
	private TcmFourDiagQuestion fourDiagQuestion;

	/**
	 * 漏诊误诊推荐信息
	 */
	private List<MissErrorDiagnosis> missErrorDiagnoses = new ArrayList<>();

	/**
	 * 用药合理性检查
	 */
	private List<MedicineCheck> medicineChecks = new ArrayList<>();

	/**
	 * 操作合理性
	 */
	private List<OperatorCheck> operatorChecks = new ArrayList<>();
}
