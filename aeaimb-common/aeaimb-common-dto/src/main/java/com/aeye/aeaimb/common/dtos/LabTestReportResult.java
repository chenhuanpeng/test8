package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 检验单结果
 */
@Data
public class LabTestReportResult {
	/**
	 * 检验单流水号
	 */
	private String labTestNumber;
	/**
	 * 检验单编码
	 * @mock 11111111
	 */
	private String labTestCode;
	/**
	 * 检验单名称
	 * @mock 血常规五项
	 */
	private String labTestName;
	/**
	 * 检验样本
	 * @mock 抗凝血
	 */
	private String labTestSample;
	/**
	 * 检验结果产生时间 格式：  yyyy-MM-dd HH:mm:ss
	 * @mock 2023-09-27 00:08:00
	 */
	private Date recordTime;
	/**
	 * 检验结果列表
	 */
	private List<LabTestItem> labTestItems = new ArrayList<>();

	@Data
	public static final class LabTestItem{
		/**
		 * 检验项目编码
		 */
		private String labTestCode;
		/**
		 * 检验项目编码
		 */
		private String labTestItemCode;
		/**
		 * 检验结果中文名称
		 * @mock 红细胞比容
		 */
		private String labTestItemName;
		/**
		 * 检验结果英文名称
		 * @mock HCT
		 */
		private String labTestItemEnName;
		/**
		 * 检验结果的值
		 * @mock 0.334
		 */
		private String labTestResult;
		/**
		 * 检验值单位
		 * @mock %
		 */
		private String labTestValueUnit;
		/**
		 * 检验值变化  正常：N    偏高：H    偏低：L
		 * @mock N
		 */
		private String labTestValueChange;
		/**
		 * 检验方法
		 * @mock CAL8000
		 */
		private String labTestMethod;
		/**
		 * 参考区间
		 * @mock 0.5-0.4
		 */
		private String normalRange;
	}

	/**
	 * 作废：0
	 * 正常：1（状态节点不传或者传1均认为是正常数据）
	 */
	private Integer state;
}
