package com.aeye.aeaimb.common.dtos;

import lombok.Data;

@Data
public class MedicalHistoryTip {
    /**
     * 既往史编码
     * @mock 00009
     */
    private String code;
    /**
     * 既往史名称
     * @mock 痔疮手术
     */
    private String name;

	/**
	 * 归因
	 */
	private String desc;
    /**
     * 既往史分类
     * 1疾病，2手术，3药物，4，检查，5，检验
     * @mock 2
     */
    private String type;

	public MedicalHistoryTip(String code, String name, String type) {
		this.code = code;
		this.name = name;
		this.type = type;
	}

	public MedicalHistoryTip() {
	}

	public enum MedicalHistoryType{
		/**
		 * 疾病, 手术, 药品, 检查, 检验, 未知
		 */
		DISEASE("1"), SURGERY("2"), DRUGS("3"), EXAMINATION("4"), LABTEST("5"), UNKNOW("-1"),
		/**
		 * 中医疾病, 症状, 中药, 药食同源, 方剂, 中成药, 外治法, 穴位, 临床路径, 临床指南, 体质, 名医医案, 治未病, 图书馆
		 */
		TCM_DISEASE("T102"),TCM_SYMPTOM("T103"),TCM_HERB("T104"),TCM_HERB_FOOD("T105"),tcm_formula("T106"),
		TCM_CTPM("T107"),TCM_ETHERAPY("T108"),TCM_ACUPOINT("T109"),TCM_PATHWAY("T110"),TCM_GUIDE("T111"),
		TCM_CONSTITUTION("T112"),TCM_CASE("T113"),TCM_E("T114"),TCM_LIBRARY("T115");
		private String code;

		MedicalHistoryType(String code) {
			this.code = code;
		}

		public static MedicalHistoryType parse(String type){
			for (MedicalHistoryType value : MedicalHistoryType.values()) {
				if (value.getCode().equals(type)){
					return value;
				}
			}
			return UNKNOW;
		}

		public String getCode() {
			return code;
		}
	}

}
