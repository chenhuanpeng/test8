package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.PositiveOrZero;

/**
 * 体格检查
 */
@Data
public class Physique {
	/**
     * 体温
	 * @mock 36.5
	 */
	@Digits(integer = 2, fraction = 2,message = "请输入正常的体温值")
	@FieldNameCN("体温")
	private Double temperature;
	/**
     * 脉搏
	 * @mock 80
	 */
	@PositiveOrZero
	@FieldNameCN("脉搏")
	private String pulse;
	/**
     * 呼吸
	 * @mock 20
	 */
	@PositiveOrZero
	@FieldNameCN("呼吸")
	private String breathe;
	/**
	 * 收缩压
	 * @mock [135, 70]
	 */
	@FieldNameCN("收缩压")
	private String systolicPressure;
	/**
	 * 舒张压
	 */
	@FieldNameCN("舒张压")
	private String diastolicPressure;

    /**
     * 空腹血糖
	 * @mock [5.2, 5.1, 4.9]
	 */
	@FieldNameCN("空腹血糖")
	private String fastingBloodSugar;
	/**
     * 结论/查体
	 * @mock 体格正常
	 */
	@FieldNameCN("查体")
	private String result;

	/**
	 * 体重
	 * @mock 60
	 */
	@FieldNameCN("体重")
	private String bodyWeight;
	/**
	 * 身高
	 * @mock 170
	 */
	@FieldNameCN("身高")
	private String bodyHeight;
}
