package com.aeye.aeaimb.common.dtos.aggregation;

import com.aeye.aeaimb.common.dtos.DiagTag;
import com.aeye.aeaimb.common.dtos.Diagnosis;
import com.aeye.aeaimb.common.dtos.Medication;
import com.aeye.aeaimb.common.dtos.TagDiagnosis;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐用药
 */
@Data
public class RecommendMedication extends TagDiagnosis {
	/**
	 * 推荐归因
	 */
	private String reason;
	/**
	 * 病症用药说明
	 */
	private List<Medication> medicationUsage = new ArrayList<>();

	/**
	 * 用于响应给前端使用
	 */
	protected String kgType;
	/**
	 * 标记 his 已经填了
	 */
	protected boolean hisMark;
}
