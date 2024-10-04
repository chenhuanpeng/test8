package com.aeye.aeaimb.core.service.dtos.aggregation;
import com.aeye.aeaimb.common.dtos.Medical;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MedicalPerson extends Medical {
    /**
     * 个人信息
     */
    private PatientInfo personInfo;

	/**
	 * 实验室检查
	 */
	private String labExams;
	/**
	 * 治疗计划
	 */
	private String treatmentPlan;
}
