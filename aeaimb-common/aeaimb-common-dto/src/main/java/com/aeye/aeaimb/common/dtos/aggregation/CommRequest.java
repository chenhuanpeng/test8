package com.aeye.aeaimb.common.dtos.aggregation;


import com.aeye.aeaimb.common.dtos.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommRequest extends ConsultationTip{
    /**
     * 开检验单/检查单信息
     */
    private List<LabTestReport> newLabTests;
    /**
     * 检验单结果信息
     */
    private List<LabTestReportResult> labTestReportResults;
    /**
     * 操作治疗信息
     */
    private List<Manitherapy> operatorSchemas = new ArrayList<>();
    /**
     * 检查单结果信息
     */
    private List<ExaminationResult> examinationResults = new ArrayList<>();
    /**
     * 处方开药信息
     */
    private List<Medication> medications = new ArrayList<>();

	/**
	 * 用血信息
	 */
	private List<BloodUse> bloodUses;

	/**
	 * 手术方案
	 */
	private List<SurgerySchema> surgerySchemas;
}
