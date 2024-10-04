package com.aeye.aeaimb.common.dtos.aggregation;

import com.aeye.aeaimb.common.dtos.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AIBigRequest  {
    /**
     * 公共参数
     */
    protected PublicParam publicParam;
    /**
     * 病历
     */
    protected Medical medical;
    /**
     * 开检验/检查单信息
     */
    protected List<LabTestReport> newLabTests = new ArrayList<>();
    /**
     * 检验单结果
     */
    protected List<LabTestReportResult> labTestReportResults = new ArrayList<>();
    /**
     * 检查单结果
     */
    protected List<ExaminationResult> examinationResults = new ArrayList<>();
    /**
     * 操作治疗信息
     */
    protected List<Manitherapy> operatorSchemas = new ArrayList<>();
    /**
     * 开处方信息
     */
    protected List<Medication> medications = new ArrayList<>();

	/**
	 * 手术信息
	 */
	protected List<SurgerySchema> surgerySchemas = new ArrayList<>();

	/**
	 * 用血信息
	 */
	protected List<BloodUse> bloodUses = new ArrayList<>();
}
