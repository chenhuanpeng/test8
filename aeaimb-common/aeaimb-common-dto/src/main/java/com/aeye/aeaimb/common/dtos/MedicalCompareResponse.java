package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 病历对比响应
 */
@Data
public class MedicalCompareResponse {

	/**
     * 当前病历诊断
	 */
	private List<Diagnosis> originDiagnoses;
	/**
	 * 目标病历诊断
	 */
	private List<Diagnosis> targetDiagnoses;

	/**
	 * 相似程度
	 * @mock 0.95
	 */
	private Double similarity;

	/**
	 * 对比项
	 */
	private List<CompareItem> compareItems = new ArrayList<>();

	@Data
	public static final class CompareItem{
		/**
		 * 对比项名称
		 * @mock 主诉
		 */
		private String name;
		/**
         * 基准值相同点
		 * @mock 1、腹痛, 2、恶心, 3、呕吐
		 */
		private List<String> originEqual;
		/**
		 * 目标值相同点
		 *@mock 1、腹痛, 2、恶心, 3、呕吐
		 */
		private List<String> targetEqual;

		/**
		 * 基准值不同点
		 * @mock 1、上腹部不适，呈隐痛。
		 * 2、尝试进食但难以保持食物下咽。
		 */
		private List<String> originDiff;
		/**
		 * 目标值不同点
		 * @mock 1、腹泻，呈水样便，频繁排便，伴随腹部绞痛。
		 * 2、食欲下降。
		 */
		private List<String> targetDiff;


		/**
		 * 相似程度
		 * @mock 0.96
		 */
		private Double similarity;
	}
}
