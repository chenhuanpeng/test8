package com.aeye.aeaimb.common.dtos.aggregation;

import com.aeye.aeaimb.common.dtos.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AIBigResponse {
	/**
	 * 科室初始化推荐
	 */
	private List<RecomInitItem> recommendInit;
    /**
     * 进一步问诊推荐信息
     */
    private RecommendAsk recommendAsk;
    /**
     * 漏诊误诊推荐信息
     */
    private List<MissErrorDiagnosis> missErrorDiagnoses = new ArrayList<>();
	/**
	 * 特殊疾病处置建议列表
	 */
	private List<DisposalAdvice> disposalAdvices;
    /**
     * 疑似诊断推荐
     */
    private List<GroupSuspectedDiag> suspectedDiagnoses = new ArrayList<>();
    /**
     * 完整治疗方案推荐信息
     */
    private List<TreatmentPlan> treatmentPlans = new ArrayList<>();
    /**
     * 检查/检验推荐
     */
    private List<LabTestReport> recommendLabTest = new ArrayList<>();
    /**
     * 用药推荐
     */
    private List<RecommendMedication> recommendMedications = new ArrayList<>();
    /**
     * 操作治疗推荐
     */
    private List<Manitherapy> recommendManitherapies = new ArrayList<>();
    /**
     * 健康知识推荐
     */
    private List<HealthyKnowledge> healthyKnowledges = new ArrayList<>();
    /**
     * 检查报告解读推荐
     */
    private List<ExaminationAnalyse> examinationAnalyses = new ArrayList<>();
    /**
     * 检验报告解读推荐
     */
    private List<LabTestAnalyse> labTestAnalyses = new ArrayList<>();
    /**
     * 用药合理性检查
     */
    private List<MedicineCheck> medicineChecks = new ArrayList<>();
    /**
     * 操作合理性
     */
    private List<OperatorCheck> operatorChecks = new ArrayList<>();
    /**
     * 检查检验合理性
     */
    private List<InspectCheck> inspectChecks = new ArrayList<>();
    /**
     * 危急重症分析能力
     */
    private List<DireIllnessAnalyse> direIllnessAnalyses = new ArrayList<>();

	/**
	 * 危重症/并发症提醒
	 */
	private List<ComplicationAnalyse> complicationAnalyses = new ArrayList<>();

	/**
	 * 手术推荐
	 */
	private List<SurgeryRecommend> recommendSurgeries = new ArrayList<>();

	/**
	 * 量表推荐
	 */
	private List<RecommendScale> recommendScales = new ArrayList<>();

	/**
	 * 手术方案合理性
	 */
	private List<SurgeryCheck> surgeryChecks = new ArrayList<>();

	/**
	 * 用血安全性
	 */
	private List<Rationality> bloodUseChecks = new ArrayList<>();

	/**
	 * 病历文书合理性
	 */
	private List<MedicalCheck> medicalChecks = new ArrayList<>();
}
