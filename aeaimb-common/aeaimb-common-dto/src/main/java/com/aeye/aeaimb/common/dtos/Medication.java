package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class Medication {
	/**
	 * 处方编号 流水号和组号无关
	 */
	private String prescriptionNo;
	/**
	 * 分组编号
	 * @mock xxx009
	 */
	private String groupNo;
	/**
	 * 处方类型 1:西药处方,2:中药处方,3:草药处方,4:治疗处方,5:其他处方
	 * @mock 1
	 *
	 */
	private String prescriptionType;
	/**
	 * 1：长期医嘱，2：临时医嘱
	 * @mock 1
	 */
	private String timelinessFlag;
	/**
     * 药品代码
	 * @mock p09999
	 */
	private String drugCode;
	/**
	 * 药品名称
	 * @mock 奥美拉唑
	 */
	private String drugName;
	/**
	 * 规格
	 * @mock （25mg*100片/瓶）
	 */
	private String specification;

	/**
	 * 剂量 / 用量
	 * @mock 2
	 */
	private String dosage;
	/**
	 * 剂量单位编码
	 * @mock 111
	 */
	private String unitCode;
	/**
	 * 单位 -> 绑定 dosage 剂量单位名称
	 * @mock 粒
	 */
	private String unit;

	/**
	 * 频率编码
	 * @mock QD
	 */
	private String frequencyCode;
	/**
	 * 频次名称
	 * @mock 一天一次
	 */
	private String frequency;

	/**
	 * 用法编码
	 * @mock
	 */
	private String drugUsageCode;
	/**
	 * 用法名称
	 * @mock 口服
	 */
	private String drugUsage;

	/**
	 * 数量
	 * @mock 2
	 */
	private String quantity;

	/**
	 * 数量单位编码
	 * @mock 1111
	 */
	private String quantityCode;
	/**
	 * 数量单位名称
	 * @mock 盒
	 */
	private String quantityName;

	/**
	 * 草药剂数（非中草药类处方默认传1）
	 * @mock 1
	 */
	private String herbalNum;
	/**
	 * 草药煎煮法
	 * @mock 小火慢煎
	 */
	private String herbalMethod;

	/**
	 * 天数
	 * @mock 7
	 */
	private String days;

	/**
	 * 剂型
	 * @mock 1
	 */
	private String dosageform;

	/**
	 * 皮试标志 0是，1否
	 * @mock 1
	 */
	private Integer skinTestFlag;
	/**
	 * 是否皮试用药（0是，1否）
	 * @mock 1
	 */
	private Integer skinDrugFlag;
	/**
	 * 皮试结果（0、阴性；1、阳性）
	 * @mock 1
	 */
	private Integer skinResult;

	/**
	 * 处方贴数
	 * @mock 7
	 */
	private String prescriptionPostsNum;
	/**
	 * 总量
	 * @mock 10
	 */
	private String totalDosage;
	/**
	 * 总量单位编码
	 * @mock 1111
	 */
	private String totalUnitCode;
	/**
	 * 总量单位
	 * @mock 克
	 */
	private String totalUnit;

	/**
	 * 处方开立时间
	 * @mock 2023-09-04 00:00:00
	 */
	private Date prescriptionDate;

	/**
	 * 备注
	 * @mock 不能喝酒
	 */
	private String remark;
	/**
	 * 注意事项
	 */
	private String notes;
	/**
	 * 药品分类
	 */
	private String medicationClassification;
	/**
	 * 作废：0
	 * 正常：1（状态节点不传或者传1均认为是正常数据）
	 */
	private Integer state;

	//医保大赛专用 1->标红 0->不标红
	private Integer yibaoMatchFlag=0;
}
