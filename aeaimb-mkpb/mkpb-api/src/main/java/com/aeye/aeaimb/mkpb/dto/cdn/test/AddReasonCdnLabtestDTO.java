package com.aeye.aeaimb.mkpb.dto.cdn.test;

import lombok.Data;

import java.io.Serializable;

/**
 * 推理规则条件(WmkgReasonCdn)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:20
 */
@Data
public class AddReasonCdnLabtestDTO implements Serializable {

	/**
	 * 条件ID
	 */
	private String cdnId;
	/**
	 * 检验编码
	 */
	private String labtestCode;
	/**
	 * 检验名称（用于显示，不关联）
	 */
	private String labtestName;


	/**
	 * 因子编码
	 */
	private String factorType;
	/**
	 * 条件权重
	 */
	private String factorWeight;
	/**
	 * 否定条件:是/否
	 */
	private String factorNeg;
	/**
	 * 必选条件:是/否
	 */
	private String factorMust;
	/**
	 * 值性质:定性、定量
	 */
	private String factorDefine;
	/**
	 * 因子数据类型：多个值、单个值
	 */
	private String factorDataType;
	/**
	 * 因子数据单位
	 */
	private String factorUnit;
	/**
	 * 规则组ID
	 */
	private String groupId;
	/**
	 * 推理ID
	 */
	private String reasonId;


	/**
	 * 配置值
	 */
	private String configValue;
	/**
	 * 运算符号：=（等于）、!=（不等于）、in ["","",""] （等于任意一个）、>（大于）、<（小于）、>=（大于等于）、<=（小于等于）、between [10,40]（介于范围）
	 */
	private String op;
	/**
	 * 目标值
	 */
	private String targetValue;
	/**
	 * 单位
	 */
	private String unit;

}

