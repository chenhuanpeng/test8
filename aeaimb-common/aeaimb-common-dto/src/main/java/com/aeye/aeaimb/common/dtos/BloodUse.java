package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用血信息
 */
@Data
public class BloodUse {
	/**
	 * 临床诊断 >= 病历中的诊断数据
	 */
	private List<Diagnosis> diagnoses;
	/**
	 * 预定输血时间
	 * @mock 2023-10-23 00:00:00
	 */
	private Date useBloodTime;
	/**
	 * 输血需求状态
	 * 1: 常态, 2: 紧急 , 3: 大量, 4: 特殊
	 * @mock 2
	 */
	private String useType;
	/**
	 * 输血目的
	 * @mock 救人
	 */
	private String purpose;

	/**
	 * 输血史
	 * @mock 无
	 */
	private String bloodUseHistory;
	/**
	 * 壬辰史
	 * @mock 无
	 */
	private String pregnancyHistory;
	/**
	 * 过敏史
	 * @mock 无
	 */
	private String allergicHistory;

	/**
	 * 输血成份
	 */
	private List<BloodComponent> bloodComponents;

	/**
	 * 输血成分其它
	 * @mock 无
	 */
	private String bloodComponentOther;

	/**
	 * 血型 A/B/AB/O
	 * @mock O
	 */
	private String bloodType;
	/**
	 * rhd
	 * 阴性/阳性
	 * @mock 阴性
	 */
	private String rhd;

	/**
	 * 血常规
	 */
	private List<BloodRoutin> bloodRoutines;

	/**
	 * 血项安全检查
	 */
	private List<SecurityCheck> securityChecks;

	/**
	 * 血项安全检查
	 */
	@Data
	public static class SecurityCheck{
		/**
		 * 血项类型编码
		 * @mock 23322
		 */
		private String bloodItemType;
		/**
		 * 血项类型名称
		 * @mock HbsAg
		 */
		private String bloodItemName;
		/**
		 * 血项数值
		 * @mock 阴性
		 */
		private String bloodItemValue;
	}

	/**
	 * 血常规
	 */
	@Data
	public static class BloodRoutin{
		/**
		 * 血项类型编码
		 * @mock 1111
		 */
		private String bloodItemType;
		/**
		 * 血项类型名称
		 * @mock 血小板
		 */
		private String bloodItemName;
		/**
		 * 血项数值
		 * @mock 244
		 */
		private String bloodItemValue;
		/**
		 * 血项数值单位
		 * @mock ×10^3/L
		 */
		private String bloodItemUnit;
		/**
		 * 血项数值单位编码
		 * @mock 23323
		 */
		private String bloodItemUnitCode;
	}

	/**
	 * 输血成份
	 */
	@Data
	public static class BloodComponent{
		/**
		 * 成份编码
		 * @mock 1132
		 */
		private String compCode;
		/**
		 * 成份名称
		 * @mock 全血
		 */
		private String compName;
		/**
		 * 容积
		 * @mock 1000
		 */
		private String cubage;
		/**
		 * 单位
		 * @mock ml
		 */
		private String unit;
		/**
		 * 单位编码
		 * @mock 23323
		 */
		private String unitCode;
	}
}
