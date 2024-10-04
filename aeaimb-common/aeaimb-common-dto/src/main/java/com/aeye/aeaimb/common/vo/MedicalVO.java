package com.aeye.aeaimb.common.vo;

import com.aeye.aeaimb.common.dtos.FieldNameCN;
import lombok.Data;

import java.util.List;

/**
 * 眼镜生成的病历 && 预问诊病历
 */
@Data
public class MedicalVO {

	/**
	 * 就诊流水号(HIS传入)
	 */
	protected String serialNumber;
	/**
	 * 主诉
	 */
	protected String complaints;

	/**
	 * 现病史
	 */
	protected String presentMedicalHistory;

	/**
	 * 既往史
	 */
	protected String previousHistory;

	/**
	 * 过敏史
	 */
	protected String allergicHistory;

	/**
	 * 家族史
	 */
	protected String familyHistory;

	/**
	 * 个人史
	 */
	protected String individualHistory;

	/**
	 * 婚育史
	 */
	protected String marriageHistory;

	/**
	 * 月经史
	 */
	protected String gynecologicalHistory;

	/**
	 * 疑似诊断
	 */
	protected List<String> diagnosis;
	/**
	 * 体温
	 */
	protected Double temperature;

	/**
	 * 脉搏
	 */
	protected String pulse;

	/**
	 * 呼吸
	 */
	protected String breathe;

	/**
	 * 舒张压
	 */
	protected String bloodPressureHigh;

	/**
	 * 收缩压
	 */
	protected String bloodPressureLow;

	/**
	 * 空腹血糖
	 */
	protected String bloodSugar;

	/**
	 * 体重
	 */
	protected String bodyWeight;

	/**
	 * 身高
	 */
	protected String bodyHeight;

	/**
	 * 查体
	 */
	protected String physicalCheck;
	/**
	 * 实验室检查/辅助检查
	 */
	protected String labExams;
}
