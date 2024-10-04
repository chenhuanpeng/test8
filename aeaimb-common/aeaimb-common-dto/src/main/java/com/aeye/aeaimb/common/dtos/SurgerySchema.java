package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.List;

/**
 * 手术
 */
@Data
public class SurgerySchema {
	/**
	 * 手术方案流水号
	 */
	private String surgeryNumber;
	/**
	 * 手术列表
	 */
	private List<Surgery> surgeries;
	/**
	 * 诊断信息
	 */
	private List<Diagnosis> diagnoses;


	/**
	 * 麻醉方式编码
	 * @mock 234324
	 */
	private String anaesthesiaCode;

	/**
	 * 麻醉方式名称
	 * @mock 全身麻醉
	 */
	private String anaesthesiaName;

	/**
	 * 切口类型编码
	 * @mock 32142
	 */
	private String incisionTypeCode;

	/**
	 * 切口类型名称
	 * @mock Ⅳ类（污秽-感染）切口
	 */
	private String incisionTypeName;

	/**
	 * 其它信息
	 * @mock 无
	 */
	private String remark;
	/**
	 * 作废：0
	 * 正常：1（状态节点不传或者传1均认为是正常数据）
	 */
	protected Integer state;
	/**
	 * 手术信息
	 */
	@Data
	public static class Surgery{
		/**
		 * 手术编码
		 * @mock 1111
		 */
		private String code;
		/**
		 * 手术名称
		 * @mock 腹腔镜胆囊切除术
		 */
		private String name;
		/**
		 * 其它信息
		 * @mock 其它信息
		 */
		private String remark;

		public Surgery() {
		}

		public Surgery(String name) {
			this.name = name;
		}
	}
}
