package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecommendAsk {
	/**
     * 关联问题项, 多个项,逗号分隔
	 */
	private List<Association> associations = new ArrayList<>();
	/**
	 * 即往病史提示项
	 */
	private List<MedicalHistoryTip> medicalHistoryTips = new ArrayList<>();

    /**
     * 其它病史提示项
	 */
	private List<String> otherHistoryTips = new ArrayList<>();

	/**
     * 体格检查提示项
	 */
	private List<CommMark> physicalTips = new ArrayList<>();
	/**
	 * 进一步问诊提示
	 */
	private List<String> consultations = new ArrayList<>();
}
